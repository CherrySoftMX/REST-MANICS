package com.cherrysoft.manics.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtToSecurityUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    String username = jwt.getSubject();
    SecurityManicUser securityUser = new SecurityManicUser(username);
    return new UsernamePasswordAuthenticationToken(securityUser, jwt, Collections.emptyList());
  }

}
