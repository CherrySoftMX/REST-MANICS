package com.cherrysoft.manics.web.dto.chapters;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "chapters", itemRelation = "chapter")
public class ChapterResponseDTO extends RepresentationModel<ChapterResponseDTO> {
  @JsonUnwrapped
  private final BaseChapterFields baseChapterFields;
}
