package com.cherrysoft.manics.exception.v2;

public class CategoryNotFoundException extends RuntimeException {

  public CategoryNotFoundException(Long id) {
    super(String.format("Category with id: %s not found.", id));
  }

}
