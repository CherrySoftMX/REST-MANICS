package com.cherrysoft.manics.exception.v2;

public class ChapterNotFoundException extends RuntimeException {

  public ChapterNotFoundException(Long id) {
    super(String.format("Chapter with id: %s not found.", id));
  }

}
