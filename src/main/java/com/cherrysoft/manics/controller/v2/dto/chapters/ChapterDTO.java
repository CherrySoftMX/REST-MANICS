package com.cherrysoft.manics.controller.v2.dto.chapters;

import com.cherrysoft.manics.controller.v2.dto.pages.PageDTO;
import com.cherrysoft.manics.model.v2.ChapterV2;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChapterDTO {
  @NotBlank(message = "The name of the chapter MUST be provided.")
  private final String name;

  @NotNull(message = "The chapter number for this chapter MUST be provided.")
  private final Integer chapterNumber;

  @NotNull(message = "The publication date of the chapter MUST be provided with the format: " + ChapterV2.MONTH_DAY_YEAR_PATTERN)
  private final LocalDate publicationDate;

  @NotNull(message = "The total pages of the chapter MUST be provided.")
  private final Integer totalPages;

  private final List<PageDTO> pages;
}
