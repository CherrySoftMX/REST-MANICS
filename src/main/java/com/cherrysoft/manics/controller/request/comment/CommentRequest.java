package com.cherrysoft.manics.controller.request.comment;

import javax.validation.constraints.NotNull;

public class CommentRequest extends CommentUpdateRequest {

  @NotNull(message = "Se requiere el id del usuario para crear un comentario.")
  private Integer userId;

  @NotNull(message = "Se requiere el id del relato para crear un comentario.")
  private Integer storyId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getStoryId() {
    return storyId;
  }

  public void setStoryId(Integer storyId) {
    this.storyId = storyId;
  }

  @Override
  public String toString() {
    return "CommentRequest{" +
        "userId=" + userId +
        ", storyId=" + storyId +
        ", content='" + getContent() + '\'' +
        '}';
  }

}
