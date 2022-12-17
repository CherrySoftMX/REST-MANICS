package com.cherrysoft.manics.controller.v2.dto.cartoons;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PageDTO {
  @NotNull(message = "The page number for this page MUST be provided.")
  private final Integer pageNumber;

  @NotBlank(message = "The image URL of the page MUST be provided.")
  private final String imageUrl;
}
