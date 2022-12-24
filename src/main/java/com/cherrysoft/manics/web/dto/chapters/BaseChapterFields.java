package com.cherrysoft.manics.web.dto.chapters;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BaseChapterFields {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(
      message = "The name of the chapter MUST be provided.",
      groups = OnCreate.class
  )
  private final String name;

  @NotNull(
      message = "The chapter number for this chapter MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer chapterNumber;

  @NotNull(
      message = "The publication date of the chapter MUST be provided with the format: " + Chapter.MONTH_DAY_YEAR_PATTERN,
      groups = OnCreate.class
  )
  private final LocalDate publicationDate;

  @NotNull(
      message = "The total pages of the chapter MUST be provided.",
      groups = OnCreate.class
  )
  private final Integer totalPages;
}
