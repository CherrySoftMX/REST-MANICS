package com.cherrysoft.manics.config.jwt;


import com.cherrysoft.manics.rest.request.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final JwtUtils jwtUtils;

  public JwtUserAuthenticationFilter(AuthenticationManager authenticationManager,
                                     JwtConfig jwtConfig,
                                     JwtUtils jwtUtils) {

    this.authenticationManager = authenticationManager;
    this.jwtConfig = jwtConfig;
    this.jwtUtils = jwtUtils;

    setFilterProcessesUrl("/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {

    try {

      UserRequest authRequest = new ObjectMapper().readValue(
          request.getInputStream(),
          UserRequest.class
      );

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          authRequest.getUsername(),
          authRequest.getPassword()
      );

      return authenticationManager.authenticate(authentication);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {

    String token = jwtUtils.createToken(
        authResult.getName(),
        authResult.getAuthorities()
    );

    response.addHeader(
        jwtConfig.getAuthorizationHeader(),
        jwtConfig.getTokenPrefix() + token
    );

  }
}
