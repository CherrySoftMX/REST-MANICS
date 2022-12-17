package com.cherrysoft.manics.exception.v2;

public class SuggestionNotFoundException extends RuntimeException {

  public SuggestionNotFoundException(Long id) {
    super(String.format("Suggestion with id: %s not found.", id));
  }

}
