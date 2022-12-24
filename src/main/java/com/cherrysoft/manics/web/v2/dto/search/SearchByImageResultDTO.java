package com.cherrysoft.manics.web.v2.dto.search;

import com.cherrysoft.manics.web.v2.dto.pages.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class SearchByImageResultDTO {
  private List<MatchingPageDTO> matchingPages;

  @Data
  public static class MatchingPageDTO {
    private Long cartoonId;
    private Long chapterId;
    private PageDTO page;
    private double score;
  }

}
