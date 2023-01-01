package com.cherrysoft.manics.model.specs;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import static java.util.Objects.nonNull;

@Data
public class CommentFilterSpec {
  private final Map<String, String> params;
  private final Pageable pageable;

  public boolean ambiguousFiltering() {
    boolean multipleFiltersSpecified = params.size() > 1;
    boolean noFilters = params.size() == 0;
    return multipleFiltersSpecified || noFilters;
  }

  public boolean filterByCommentsOfUser() {
    return nonNull(params.get("userId"));
  }

  public boolean filterByCommentsOfCartoon() {
    return nonNull(params.get("cartoonId"));
  }

  public boolean filterByCommentsOfComment() {
    return nonNull(params.get("commentId"));
  }

  public Long getUserId() {
    return Long.parseLong(params.get("userId"));
  }

  public Long getCartoonId() {
    return Long.parseLong(params.get("cartoonId"));
  }

  public Long getCommentId() {
    return Long.parseLong(params.get("commentId"));
  }

}
