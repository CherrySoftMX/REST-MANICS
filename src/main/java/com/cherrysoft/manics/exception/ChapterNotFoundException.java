package com.cherrysoft.manics.exception;

public class ChapterNotFoundException extends RuntimeException {

  public ChapterNotFoundException(Long id) {
    super(String.format("Chapter with id: %s not found.", id));
  }

}
