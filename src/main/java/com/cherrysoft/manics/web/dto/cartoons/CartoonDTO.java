package com.cherrysoft.manics.web.dto.cartoons;

import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartoonDTO {
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

  private final List<ChapterDTO> chapters;

  @JsonProperty("categories")
  private final Set<Long> categoryIds;
}
