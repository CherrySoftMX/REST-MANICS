package com.cherrysoft.manics.security.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BearerTokenAuthenticationLoggingEntryPoint implements AuthenticationEntryPoint {
  private final BearerTokenAuthenticationEntryPoint entryPoint = new BearerTokenAuthenticationEntryPoint();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
    String uri = request.getRequestURI();
    log.warn("Someone without authentication attempted to access the URI: {}", uri);
    entryPoint.commence(request, response, authException);
  }

}
