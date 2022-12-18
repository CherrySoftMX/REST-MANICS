package com.cherrysoft.manics.exception.v2;

import com.cherrysoft.manics.model.v2.CartoonType;

public class CartoonNotFoundException extends RuntimeException {

  public CartoonNotFoundException(Long id, CartoonType cartoonType) {
    super(String.format("%s with id: %s not found.", cartoonType.name(), id));
  }

  public CartoonNotFoundException(Long id) {
    super(String.format("Cartoon with id: %s not found.", id));
  }

}
