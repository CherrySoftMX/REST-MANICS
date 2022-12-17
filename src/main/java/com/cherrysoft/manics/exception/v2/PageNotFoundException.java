package com.cherrysoft.manics.exception.v2;

public class PageNotFoundException extends RuntimeException {

  public PageNotFoundException(Long id) {
    super(String.format("Page with id: %s not found.", id));
  }

}
