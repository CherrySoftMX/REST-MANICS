package com.cherrysoft.manics.exception.legacy;

public class CategoryInUseException extends RuntimeException {

  public CategoryInUseException() {
    super("La categoría está en uso");
  }

}
