/*
 * ComiXed - A digital comic book library management application.
 * Copyright (C) 2019, The ComiXed Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package org.comixed.service.library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.comixed.adaptors.ArchiveType;
import org.comixed.model.comic.Comic;
import org.comixed.model.tasks.TaskType;
import org.comixed.model.user.ComiXedUser;
import org.comixed.model.user.LastReadDate;
import org.comixed.repositories.comic.ComicRepository;
import org.comixed.repositories.library.LastReadDatesRepository;
import org.comixed.service.comic.PageCacheService;
import org.comixed.service.task.TaskService;
import org.comixed.service.user.ComiXedUserException;
import org.comixed.service.user.UserService;
import org.comixed.task.model.ConvertComicsWorkerTask;
import org.comixed.task.model.MoveComicsWorkerTask;
import org.comixed.task.runner.Worker;
import org.comixed.utils.Utils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class LibraryService {
  @Autowired private TaskService taskService;
  @Autowired private UserService userService;
  @Autowired private ComicRepository comicRepository;
  @Autowired private LastReadDatesRepository lastReadDateRepository;
  @Autowired private ReadingListService readingListService;
  @Autowired private ObjectFactory<ConvertComicsWorkerTask> convertComicsWorkerTaskObjectFactory;
  @Autowired private Worker worker;
  @Autowired private Utils utils;
  @Autowired private PageCacheService pageCacheService;
  @Autowired private ObjectFactory<MoveComicsWorkerTask> moveComicsTaskObjectFactory;

  public List<Comic> getComicsUpdatedSince(
      String email, Date latestUpdatedDate, int maximumComics, long lastComicId) {
    log.debug(
        "Finding up to {} comics updated since {} for {}", maximumComics, latestUpdatedDate, email);

    List<Comic> comics =
        this.comicRepository.getLibraryUpdates(latestUpdatedDate, PageRequest.of(0, maximumComics));
    List<Comic> result = new ArrayList<>();

    final long timestamp = latestUpdatedDate.getTime();
    for (int index = 0; index < comics.size(); index++) {
      Comic comic = comics.get(index);
      final long thisTimestamp = comic.getDateLastUpdated().getTime();
      final Long thisId = comic.getId();
      log.debug(
          "Checking comic: timestamp: {} >= {} id: {} > {}",
          thisTimestamp,
          timestamp,
          thisId,
          lastComicId);
      boolean include = thisTimestamp > timestamp || thisId > lastComicId;

      if (include) {
        log.debug("Including comic: id={}", thisId);
        result.add(comic);
      }
    }

    if (!result.isEmpty()) {
      log.debug("Loading reading lists");
      this.readingListService.getReadingListsForComics(email, result);
    }

    log.debug("Returning {} updated comic{}", result.size(), result.size() == 1 ? "" : "s");
    return result;
  }

  public List<LastReadDate> getLastReadDatesSince(String email, Date lastReadDate)
      throws ComiXedUserException {
    ComiXedUser user = this.userService.findByEmail(email);
    log.debug(
        "Retrieving all last read dates updated since {} for {}", lastReadDate, user.getEmail());
    return this.lastReadDateRepository.findAllForUser(user.getId(), lastReadDate);
  }

  public long getProcessingCount() {
    log.debug("Getting processing count");
    return this.taskService.getTaskCount(TaskType.PROCESS_COMIC);
  }

  public void convertComics(
      List<Long> comicIdList, ArchiveType targetArchiveType, boolean renamePages) {
    log.debug(
        "Converting {} comic{} to {}{}",
        comicIdList.size(),
        comicIdList.size() == 1 ? "" : "s",
        targetArchiveType,
        renamePages ? " (renaming pages)" : "");
    List<Comic> comics = new ArrayList<>();
    for (long id : comicIdList) {
      comics.add(this.comicRepository.getById(id));
    }
    log.debug("Getting save comics worker task");
    ConvertComicsWorkerTask task = this.convertComicsWorkerTaskObjectFactory.getObject();

    task.setComicList(comics);
    task.setTargetArchiveType(targetArchiveType);
    task.setRenamePages(renamePages);

    log.debug("Queueing save comics worker task");
    this.worker.addTasksToQueue(task);
  }

  @Transactional
  public List<Comic> consolidateLibrary(boolean deletePhysicalFiles) {
    log.debug("Consolidating library: delete physical files={}", deletePhysicalFiles);

    List<Comic> result = this.comicRepository.findAllMarkedForDeletion();

    for (Comic comic : result) {
      log.debug("Removing deleted comics from library");
      this.comicRepository.delete(comic);

      if (deletePhysicalFiles) {
        String filename = comic.getFilename();
        File file = comic.getFile();
        log.debug("Deleting physical file: {}", filename);
        this.utils.deleteFile(file);
      }
    }
    return result;
  }

  /**
   * Removes all files in the image cache directory.
   *
   * @throws LibraryException if an error occurs
   */
  public void clearImageCache() throws LibraryException {
    String directory = this.pageCacheService.getRootDirectory();
    log.debug("Clearing the image cache: {}", directory);
    try {
      this.utils.deleteDirectoryContents(directory);
    } catch (IOException error) {
      throw new LibraryException("failed to clean image cache directory", error);
    }
  }

  /**
   * Moves all comics in the library, renaming them using the specified naming rule.
   *
   * @param deletePhysicalFiles
   * @param directory the root directory
   * @param renamingRule the name rule
   */
  public void moveComics(Boolean deletePhysicalFiles, String directory, String renamingRule) {
    log.debug("Creating move comics task");
    MoveComicsWorkerTask task = this.moveComicsTaskObjectFactory.getObject();
    log.debug("Setting directory: {}", directory);
    task.setDirectory(directory);
    log.debug("Setting renaming rule: {}", renamingRule);
    task.setRenamingRule(renamingRule);
    log.debug("Enqueuing task");
    this.worker.addTasksToQueue(task);
  }
}
