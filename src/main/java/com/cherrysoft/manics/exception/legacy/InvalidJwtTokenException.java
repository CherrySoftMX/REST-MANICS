package com.cherrysoft.manics.exception.legacy;

public class InvalidJwtTokenException extends RuntimeException {

  public InvalidJwtTokenException() {
    super("El token ha expirado o es inválido");
  }

}
