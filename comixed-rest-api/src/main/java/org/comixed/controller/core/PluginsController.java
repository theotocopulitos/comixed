/*
 * ComiXed - A digital comic book library management application.
 * Copyright (C) 2020, The ComiXed Project
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

package org.comixed.controller.core;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.comixed.plugins.PluginManager;
import org.comixed.plugins.model.PluginDescriptor;
import org.comixed.views.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <code>PLuginsController</code> accepts requests relating to the plugin subsystem.
 *
 * @author Darryl L. Pierce
 */
@RestController
@RequestMapping("/api")
@Log4j2
public class PluginsController {
  @Autowired private PluginManager pluginManager;

  @GetMapping(value = "/plugins", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(View.PluginList.class)
  public List<PluginDescriptor> getList() {
    log.info("Fetching the list of plugins");
    return this.pluginManager.getPluginList();
  }
}
