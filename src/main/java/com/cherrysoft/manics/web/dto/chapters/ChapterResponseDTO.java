package com.cherrysoft.manics.web.dto.chapters;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class ChapterResponseDTO {
  @JsonUnwrapped
  private final BaseChapterFields baseChapterFields;
}
