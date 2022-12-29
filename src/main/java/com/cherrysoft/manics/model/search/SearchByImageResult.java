package com.cherrysoft.manics.model.search;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class SearchByImageResult {
  private List<MatchingPage> matchingPages;

  public void addMatchingPage(MatchingPage matchingPage) {
    if (isNull(this.matchingPages)) {
      this.matchingPages = new ArrayList<>();
    }
    this.matchingPages.add(matchingPage);
  }

  @Data
  public static class MatchingPage {
    private final Long cartoonId;
    private final Long chapterId;
    private final Page page;
    private final double score;

    public MatchingPage(Page page, double score) {
      this.page = page;
      this.score = score;
      Chapter chapter = page.getChapter();
      this.chapterId = chapter.getId();
      this.cartoonId = chapter.getCartoon().getId();
    }

  }

}
