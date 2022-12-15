package com.manics.rest.exception;

public class UsuarioRegistradoException extends BadRequestException {

  public UsuarioRegistradoException(String username) {
    super(String.format("El nombre de usuario: %s ya se encuentra registrado", username));
  }

}
