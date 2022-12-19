package com.cherrysoft.manics.web.v2.dto.cartoons;

import com.cherrysoft.manics.web.v2.dto.chapters.ChapterDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartoonDTO {
  @JsonUnwrapped
  private final BaseCartoonFields baseCartoonFields;

  private final List<ChapterDTO> chapters;

  @JsonProperty("categories")
  private final Set<Long> categoryIds;
}
