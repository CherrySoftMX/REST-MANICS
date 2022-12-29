package com.cherrysoft.manics.security;

import com.cherrysoft.manics.security.logging.BearerTokenAccessDeniedLoggingHandler;
import com.cherrysoft.manics.security.logging.BearerTokenAuthenticationLoggingEntryPoint;
import com.cherrysoft.manics.security.service.ManicUserDetailsService;
import com.cherrysoft.manics.security.utils.KeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtToSecurityUserConverter jwtToUserConverter;
  private final KeyUtils keyUtils;
  private final PasswordEncoder passwordEncoder;
  private static final String[] AUTH_WHITELIST = {
      "/login",
      "/register",
      "/refresh-token",
      "/swagger-ui/**",
      "/swagger-resources/**",
      "/v3/api-docs/**"
  };

  @Bean
  @Qualifier("jwtRefreshTokenAuthProvider")
  JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
    JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
    provider.setJwtAuthenticationConverter(jwtToUserConverter);
    return provider;
  }

  @Bean
  DaoAuthenticationProvider authManager(ManicUserDetailsService userDetailsService) {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(auth -> auth
            .antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToUserConverter))
        )
        .exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(new BearerTokenAuthenticationLoggingEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedLoggingHandler())
        )
        .build();
  }

  @Bean
  @Primary
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey
        .Builder(keyUtils.getAccessTokenPublicKey())
        .privateKey(keyUtils.getAccessTokenPrivateKey())
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  @Primary
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder
        .withPublicKey(keyUtils.getAccessTokenPublicKey())
        .build();
  }

  @Bean
  @Qualifier("jwtRefreshTokenEncoder")
  JwtEncoder jwtRefreshTokenEncoder() {
    JWK jwk = new RSAKey
        .Builder(keyUtils.getRefreshTokenPublicKey())
        .privateKey(keyUtils.getRefreshTokenPrivateKey())
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  @Qualifier("jwtRefreshTokenDecoder")
  JwtDecoder jwtRefreshTokenDecoder() {
    return NimbusJwtDecoder
        .withPublicKey(keyUtils.getRefreshTokenPublicKey())
        .build();
  }

}
