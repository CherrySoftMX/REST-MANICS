package com.cherrysoft.manics.rest.request.user;

public class SuggestionRequest {

  private Integer userId;

  private String content;

  public SuggestionRequest() {

  }

  public Integer getUserId() {
    return userId;
  }

  public String getContent() {
    return content;
  }

}
