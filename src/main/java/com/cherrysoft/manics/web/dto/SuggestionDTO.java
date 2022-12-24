package com.cherrysoft.manics.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SuggestionDTO {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(message = "Content for the suggestion MUST be provided.")
  private final String content;
}
