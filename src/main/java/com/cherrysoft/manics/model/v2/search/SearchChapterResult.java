package com.cherrysoft.manics.model.v2.search;

import lombok.Data;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
public class SearchChapterResult {
  private final List<SearchingChapter> matchingChapters;

  public Set<Long> getMatchingChapterIds() {
    return matchingChapters.stream()
        .map(SearchingChapter::getId)
        .collect(toSet());
  }

}
