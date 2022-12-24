package com.cherrysoft.manics.web.dto.cartoons;

import com.cherrysoft.manics.web.dto.validation.OnCreate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Year;

@Data
public class BaseCartoonFields {
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
