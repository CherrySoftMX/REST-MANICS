package com.cherrysoft.manics.web.dto.chapters;

import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.List;

@Data
public class ChapterDTO {
  @JsonUnwrapped
  private final BaseChapterFields baseChapterFields;

  private final List<CartoonPageDTO> pages;
}
