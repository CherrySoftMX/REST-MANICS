package com.cherrysoft.manics.config.lagacy.jwt;

import com.google.common.base.Strings;
import com.cherrysoft.manics.exception.legacy.InvalidJwtTokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  public JwtTokenVerifier(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String token = jwtUtils.resolveToken(request);

    try {

      if (isValidToken(token)) {
        Authentication auth = jwtUtils.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
        return;
      }

    } catch (InvalidJwtTokenException ex) {
      SecurityContextHolder.clearContext();
      throw ex;
    }

    filterChain.doFilter(request, response);
  }

  private boolean isValidToken(String token) {
    return !Strings.isNullOrEmpty(token)
        && jwtUtils.validateToken(token);
  }

}
