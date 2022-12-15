package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.Comic;
import com.cherrysoft.manics.model.Manga;
import com.cherrysoft.manics.model.core.Story;
import com.cherrysoft.manics.rest.request.StoryRequest;
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
