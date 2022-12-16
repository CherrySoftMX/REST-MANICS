package com.cherrysoft.manics.exception;

public class InvalidJwtTokenException extends RuntimeException {

  public InvalidJwtTokenException() {
    super("El token ha expirado o es inválido");
  }

}
