package com.cherrysoft.manics.model.v2.search;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SearchCartoonResult {
  private final List<SearchingCartoon> matchingCartoons;

  public Set<Long> getMatchingCartoonIds() {
    return matchingCartoons.stream()
        .map(SearchingCartoon::getId)
        .collect(Collectors.toSet());
  }

}
