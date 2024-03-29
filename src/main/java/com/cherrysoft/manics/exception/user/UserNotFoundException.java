package com.cherrysoft.manics.exception.user;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long id) {
    super(String.format("User with id: %s not found", id));
  }

  public UserNotFoundException(String username) {
    super(String.format("User with username: %s not found", username));
  }

}
