package com.cherrysoft.manics.web.legacy.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {

  @NotEmpty(message = "El nombre de usuario no puede estar vacío.")
  @NotNull(message = "Se requiere un nombre de usuario para registrarse.")
  @Size(min = 5, max = 16, message = "La longitud del nombre de usuario debe tener de 5 a 16 caracteres.")
  private String username;

  @NotEmpty(message = "La contraseña no puede estar vacía.")
  @NotNull(message = "Se requiere una contraseña para registrarse.")
  @Size(min = 5, max = 16, message = "La longitud de la contraseña debe tener de 5 a 16 caracteres.")
  private String password;

  @NotEmpty(message = "El email no puede estar vacío.")
  @NotNull(message = "Un email es requerido para el registro.")
  @Email(message = "El formato del email es inválido.")
  private String email;

  public UserRequest() {

  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "UserRequest{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

}
