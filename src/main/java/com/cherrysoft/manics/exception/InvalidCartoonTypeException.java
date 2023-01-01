package com.cherrysoft.manics.exception;

public class InvalidCartoonTypeException extends RuntimeException {

  public InvalidCartoonTypeException(String invalidType) {
    super(String.format("The provided cartoon type: %s is not valid.", invalidType));
  }
}
