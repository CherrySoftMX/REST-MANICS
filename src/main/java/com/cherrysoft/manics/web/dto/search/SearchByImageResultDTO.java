package com.cherrysoft.manics.web.dto.search;

import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import lombok.Data;

import java.util.List;

@Data
public class SearchByImageResultDTO {
  private List<MatchingPageDTO> matchingPages;

  @Data
  public static class MatchingPageDTO {
    private Long cartoonId;
    private Long chapterId;
    private CartoonPageDTO page;
    private double score;
  }

}
