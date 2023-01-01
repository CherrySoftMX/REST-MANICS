package com.cherrysoft.manics.web.dto;

import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "comments", itemRelation = "comment")
public class CommentDTO extends RepresentationModel<CommentDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(
      message = "The content of the comment MUST be provided.",
      groups = OnCreate.class
  )
  private final String content;
}
