package com.cherrysoft.manics.model.search;

import com.cherrysoft.manics.model.CartoonPage;
import lombok.Data;

@Data
public class MatchingPage {
  private final CartoonPage page;
  private final double score;

  public MatchingPage(CartoonPage page, double score) {
    this.page = page;
    this.score = score;
  }

}
