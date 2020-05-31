# Release Notes
## 0.6-0.rc1 - May 31, 202
  280  [Issue #267] Comics can be deleted and child objects removed
  278  [Issue #277] Edit reading list dialog closes correctly.
  277  Close buttons aren't closing the reading list edit dialog.
  276  [Issue #238] Remove direct dependency on log4j2.
  273  [Issue #233] Reading lists can be created or edited from the library navigation tree.
  271  [Issue #270] Fix wrong message when using invalid credentials to log.
  269  [Issue #241] Rework the rescan and backup sections.
  268  [Issue #240] Add description to the consolidate library admin component.
  267  Foriegn key constraint violation when deleting a comic in a reading list
  266  [Issue #213] Adjust seach icon on volume list 
  265  [Issue #213] Combined the search icon with the search input field.
  264  [Issue #208] Allow scraping of series with "/" in the name
  262  [Issue #260] Merge the updated reading lists into the library when retrieved
  261  [Issue #226] Include the reading lists for comics when they're returned in an update
  260  Return updated reading lists with each library update
  258  [Issue #219] Allow encoded values in the issue number.
  257  Bump jquery from 3.4.0 to 3.5.0 in /comixed-frontend
  256  [Issue #245] Allow multiple browser tabs to share one login session.
  255  [Issue #252] Ensure the history title matches the library page content.
  254  [Issue #253] Fix the links in ComicListItem.
  253  Links on the ComicListItem component are broken
  252  Edge tab title is "library-page.title"
  251  [Issue #211] Remove the addition to the issue count.
  250  [Issue #16] Show duplicate comics
  249  [Issue #248] Bump WebP-ImageIO to v0.1.2.
  248  Bump WebP-ImageIO to v0.1.2
  247  Bump jackson-databind from 2.9.10.3 to 2.9.10.4
  246  [Issue #222] Fix collection links and breadcrumb trail.
  245  Allow multiple browser tabs on a single login session
  241  Rescan and Export library button text no longer fits within the button image.
  240  Description for "Consolidate Library Files" button.
  238  Multiple SLF4j JARs in build
  235  [Issue #231] Include the volume with the series name when building collections.
  234  [Issue #230] Remove the collections components, refactor the library page to show collections.
  233  Users can create a new reading list from the navigation tree
  232  [Issue #225] Return a default image when the publisher does not exist.
  231  Include the volume when grouping comics.
  230  Refactor the old collections links to use the new navigation method
  228  [Issue #223] Add filtering the to library navigation tree.
  227  [Issue #221] Show comics selected from the navigation tree.
  226  Comics include their reading lists when retrieved
  225  Don't throw an exception when a publisher is not found by name.
  224  [Issue #220] Add the library navigation tree
  223  The library tree nodes displayed can be filtered.
  222  Remove the collections view components.
  221  Clicking on a library tree node displays only the comics for that node
  220  Add a library navigation sidebar
  219  Unable to scrape ½ issue.
  218  [Issue #207] When building the list of publishers, treat imprints as publishers.
  216  Collections - Null publishers shown, but not selectable.
  215  [Issue #207] Show imprint with publisher
  214  Collections breadcrumb not updating
  213  Move the search icon (magnifying glass) a fraction to the left of the search text box.
  211  Scraping - Issue count is one higher than expected in some cases.
  208  Scraping comic with a "/" in the title fails.
  206  [Issue #205] Store imprints as their own records in the publishers details table.
  205  When scraping, comics from Imprints having primary Publisher recorded in PUBLISHER table.
  203  [Issue #156] Fix null reference when context menu is not defined.
  201  [Issue #11] Clear the selection when opening a single comic.
  199  [Issue #197] Move image type identification in the base archive adaptor.
  198  [Issue #20] Enable consolidating the library.
  197  Move the list of image MIME types to entryloaders.properties
  196  [Issue #144] Replace logging with Log4j2
  195  [Issue #192] Show details of the Publisher.
  194  [Issue #193] Replace ngx-logger with @angular-ru/logger.
  193  Replace ngx-logger with @angular-ru/logger
  192  Display the details for a publisher
  191  [Issue #120] Add persistent Publisher data model.
  190  [Issue #188] Add explicit missing comic field to comic state.
  188  Add a proper field to the comic state to identify when the comic is not found
  187  [Issue #137] Comics can be converted to another archive format
  182  Admin --> Users, New User screen not rendering properly.
  177  [Issue #73] Return an image placeholder when one isn't found on the backend
  175  Return a default image when there's an error on loading a page
  172  [Issue #170] Upgrade Angular to v8.
  170  Upgrade to Angular 8
  164  [Issue #159] Add WebP support.
  163  Rename TaskType constants to meet coding conventiosn.
  159  Add WebP image support.
  156  Marking a comic for deletion disables the context menu.
  144  Replacing Java logger with @Log4js annotations.
  143  Make worker tasks persistent
  142  [Issue #132] Move the context menus to a central feature
  137  Comics can be converted to another format
  132  Move the context menu to the comic cover component.
  122  Show the publisher's icon next to their name in the collections publishers view
  121  Display the publisher thumbnail next to the publisher name on the comic details view.
  120  Add the Publisher persistent data model.
  108  Add a command line option to the startup scripts to override the image caching directory
  100  Add frontend logging support
   72  Importing smart lists from comicrack
   40  User profile editing...
   20  Library consolidation
   18  The user can edit the details of a comic
   16  Show duplicate comics
   11  After performing any action on selected comics, they should be removed from the selection state.
