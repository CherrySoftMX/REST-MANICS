package com.cherrysoft.manics.exception;

public class CategoryInUseException extends RuntimeException {

  public CategoryInUseException() {
    super("La categoría está en uso");
  }

}
