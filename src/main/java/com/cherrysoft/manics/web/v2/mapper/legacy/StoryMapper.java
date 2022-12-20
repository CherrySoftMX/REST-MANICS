package com.cherrysoft.manics.web.v2.mapper.legacy;

import com.cherrysoft.manics.model.legacy.Comic;
import com.cherrysoft.manics.model.legacy.Manga;
import com.cherrysoft.manics.model.legacy.core.Story;
import com.cherrysoft.manics.web.legacy.request.StoryRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public abstract class StoryMapper {

  @AfterMapping
  protected void updateBidirectionalRelationships(@MappingTarget Story story) {
    if (!isNull(story.getChapters())) {
      story.getChapters().forEach(chapter -> {
        if (!isNull(chapter.getPages())) {
          chapter.getPages().forEach(page -> page.setChapter(chapter));
          chapter.setStory(story);
        }
      });
    }
  }

  public abstract Manga storyRequestToManga(StoryRequest storyRequest);

  public abstract Comic storyRequestToComic(StoryRequest storyRequest);

}