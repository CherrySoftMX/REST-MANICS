package com.cherrysoft.manics.exception;

public class PageNotFoundException extends RuntimeException {

  public PageNotFoundException(Long id) {
    super(String.format("Page with id: %s not found.", id));
  }

}
