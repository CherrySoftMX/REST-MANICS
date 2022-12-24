package com.cherrysoft.manics.exception;

public class SuggestionNotFoundException extends RuntimeException {

  public SuggestionNotFoundException(Long id) {
    super(String.format("Suggestion with id: %s not found.", id));
  }

}
