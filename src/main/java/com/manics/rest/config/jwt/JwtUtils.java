package com.manics.rest.config.jwt;

import com.manics.rest.config.UserDetailsServiceImp;
import com.manics.rest.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtils {

  private final SecretKey secretKey;
  private final JwtConfig jwtConfig;
  private final UserDetailsServiceImp userDetailsService;

  @Autowired
  public JwtUtils(
      SecretKey secretKey,
      JwtConfig jwtConfig,
      UserDetailsServiceImp userDetailsService) {

    this.secretKey = secretKey;
    this.jwtConfig = jwtConfig;
    this.userDetailsService = userDetailsService;
  }

  public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", authorities);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
        .signWith(secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(jwtConfig.getAuthorizationHeader());

    if (!Objects.isNull(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenPrefix())) {
      return bearerToken.replace(jwtConfig.getTokenPrefix(), "");
    }

    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidJwtTokenException();
    }
  }

}
