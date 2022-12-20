package com.cherrysoft.manics.web.v2.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
  private final String username;
  private final String accessToken;
  private final String refreshToken;
}
