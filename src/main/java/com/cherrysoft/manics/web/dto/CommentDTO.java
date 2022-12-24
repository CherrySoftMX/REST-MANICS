package com.cherrysoft.manics.web.dto;

import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDTO {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(
      message = "The content of the comment MUST be provided.",
      groups = OnCreate.class
  )
  private final String content;
}
