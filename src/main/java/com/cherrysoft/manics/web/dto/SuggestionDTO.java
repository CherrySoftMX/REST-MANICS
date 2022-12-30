package com.cherrysoft.manics.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "suggestions", itemRelation = "suggestion")
public class SuggestionDTO extends RepresentationModel<SuggestionDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(message = "Content for the suggestion MUST be provided.")
  private final String content;
}
