package com.cherrysoft.manics.model.v2.specs;

import lombok.Data;

import java.util.Map;

import static java.util.Objects.nonNull;

@Data
public class CommentFilterSpec {
  private final Map<String, String> params;

  public boolean ambiguousFiltering() {
    boolean bothFiltersSpecified = filterByCartoonComments() && filterByUserComments();
    boolean noFilters = !filterByCartoonComments() && !filterByUserComments();
    return bothFiltersSpecified || noFilters;
  }

  public boolean filterByUserComments() {
    return nonNull(params.get("userId"));
  }

  public boolean filterByCartoonComments() {
    return nonNull(params.get("cartoonId"));
  }

  public Long getUserId() {
    return Long.parseLong(params.get("userId"));
  }

  public Long getCartoonId() {
    return Long.parseLong(params.get("cartoonId"));
  }

}
