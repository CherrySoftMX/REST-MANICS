package com.manics.rest.rest.request.chapter;

import javax.validation.constraints.NotNull;

public class ChapterRequest extends ChapterUpdateRequest {

  @NotNull
  private Integer storyId;

  public Integer getStoryId() {
    return storyId;
  }

  public void setStoryId(Integer storyId) {
    this.storyId = storyId;
  }

}
