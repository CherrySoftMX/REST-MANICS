package com.cherrysoft.manics.web.v2.dto.cartoons;

import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Year;

@Data
public class BaseCartoonFields {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(
      message = "The name of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final String name;

  @NotBlank(
      message = "The author of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final String author;

  @NotNull(
      message = "Publication year of the cartoon MUST be provided.",
      groups = OnCreate.class
  )
  private final Year publicationYear;

  @NotNull(
      message = "Available chapters count MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer availableChapters;
}
