package com.cherrysoft.manics.exception.v2.user;

public class UsernameAlreadyTakenException extends RuntimeException {

  public UsernameAlreadyTakenException(String username) {
    super(String.format("The username: %s is already taken!", username));
  }

}