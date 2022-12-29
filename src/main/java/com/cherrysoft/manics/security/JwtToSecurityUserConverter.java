package com.cherrysoft.manics.security;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.service.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToSecurityUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
  private final ManicUserService userService;

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    String username = jwt.getSubject();
    ManicUser user = userService.getUserByUsername(username);
    SecurityManicUser securityUser = new SecurityManicUser(user);
    return new UsernamePasswordAuthenticationToken(securityUser, jwt, securityUser.getAuthorities());
  }

}
