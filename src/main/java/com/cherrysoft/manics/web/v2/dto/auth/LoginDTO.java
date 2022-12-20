package com.cherrysoft.manics.web.v2.dto.auth;

import lombok.Data;

@Data
public class LoginDTO {
  private final String username;
  private final String password;
}
