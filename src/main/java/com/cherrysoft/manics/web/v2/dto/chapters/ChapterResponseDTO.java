package com.cherrysoft.manics.web.v2.dto.chapters;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class ChapterResponseDTO {
  @JsonUnwrapped
  private final BaseChapterFields baseChapterFields;
}
