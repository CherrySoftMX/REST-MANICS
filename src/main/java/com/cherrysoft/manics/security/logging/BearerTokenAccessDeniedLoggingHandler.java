package com.cherrysoft.manics.security.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BearerTokenAccessDeniedLoggingHandler implements AccessDeniedHandler {
  private final BearerTokenAccessDeniedHandler deniedHandler = new BearerTokenAccessDeniedHandler();

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException
  ) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    String uri = request.getRequestURI();
    log.warn("The user with username {} attempted to access the protected URL: {}", username, uri);
    deniedHandler.handle(request, response, accessDeniedException);
  }

}
