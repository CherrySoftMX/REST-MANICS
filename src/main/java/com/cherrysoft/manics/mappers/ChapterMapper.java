package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.core.Chapter;
import com.cherrysoft.manics.rest.request.chapter.ChapterRequest;
import com.cherrysoft.manics.rest.request.chapter.ChapterUpdateRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public abstract class ChapterMapper {

  @AfterMapping
  protected void updateBidirectionalRelationships(@MappingTarget Chapter chapter) {
    if (!isNull(chapter.getPages())) {
      chapter.getPages().forEach((page) -> page.setChapter(chapter));
    }
  }

  public abstract Chapter chapterRequestToChapter(ChapterRequest request);

  public abstract Chapter chapterUpdateRequestToChapter(ChapterUpdateRequest request);

}