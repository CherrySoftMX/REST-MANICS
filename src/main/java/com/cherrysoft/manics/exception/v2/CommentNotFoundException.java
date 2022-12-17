package com.cherrysoft.manics.exception.v2;

public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException(Long id) {
    super(String.format("Comment with id: %s not found.", id));
  }

}
