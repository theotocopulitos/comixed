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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.package
 * org.comixed;
 */

import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { TranslateModule } from "@ngx-translate/core";
import { ComicCoverComponent } from "../../comic/comic-cover/comic-cover.component";
import { ComicCoverUrlPipe } from "../../../../pipes/comic-cover-url.pipe";
import { REGULAR_COMIC } from "../../../../models/comics/comic.fixtures";
import { ComicListItemComponent } from "./comic-list-item.component";

describe("ComicListItemComponent", () => {
  let component: ComicListItemComponent;
  let fixture: ComponentFixture<ComicListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, TranslateModule.forRoot()],
      declarations: [
        ComicListItemComponent,
        ComicCoverComponent,
        ComicCoverUrlPipe
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ComicListItemComponent);
    component = fixture.componentInstance;
    component.comic = REGULAR_COMIC;
    component.same_height = true;
    component.cover_size = 640;
    fixture.detectChanges();
  }));

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
