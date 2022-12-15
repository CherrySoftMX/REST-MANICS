package com.manics.rest.exception;

public class CategoryInUseException extends RuntimeException {

  public CategoryInUseException() {
    super("La categoría está en uso");
  }

}
