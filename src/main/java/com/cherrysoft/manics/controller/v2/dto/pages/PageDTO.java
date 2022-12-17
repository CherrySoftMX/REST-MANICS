package com.cherrysoft.manics.controller.v2.dto.pages;

import com.cherrysoft.manics.controller.v2.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PageDTO {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotNull(
      message = "The page number for this page MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer pageNumber;

  @NotBlank(
      message = "The image URL of the page MUST be provided.",
      groups = OnCreate.class
  )
  private final String imageUrl;
}
