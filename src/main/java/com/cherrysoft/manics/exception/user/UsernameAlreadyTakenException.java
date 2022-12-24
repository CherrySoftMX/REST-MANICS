package com.cherrysoft.manics.exception.user;

public class UsernameAlreadyTakenException extends RuntimeException {

  public UsernameAlreadyTakenException(String username) {
    super(String.format("The username: %s is already taken!", username));
  }

}
