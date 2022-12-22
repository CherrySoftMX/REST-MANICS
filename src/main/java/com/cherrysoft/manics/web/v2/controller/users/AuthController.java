package com.cherrysoft.manics.web.v2.controller.users;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.security.TokenGenerator;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import com.cherrysoft.manics.web.v2.dto.auth.LoginDTO;
import com.cherrysoft.manics.web.v2.dto.auth.TokenDTO;
import com.cherrysoft.manics.web.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.v2.mapper.v2.ManicUserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
  private final ManicUserService userService;
  private final ManicUserMapper userMapper;
  private final TokenGenerator tokenGenerator;
  private final DaoAuthenticationProvider daoAuthenticationProvider;
  private final JwtAuthenticationProvider refreshTokenAuthProvider;

  public AuthController(
      ManicUserService userService,
      ManicUserMapper userMapper,
      TokenGenerator tokenGenerator,
      DaoAuthenticationProvider daoAuthenticationProvider,
      @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider refreshTokenAuthProvider
  ) {
    this.userService = userService;
    this.userMapper = userMapper;
    this.tokenGenerator = tokenGenerator;
    this.daoAuthenticationProvider = daoAuthenticationProvider;
    this.refreshTokenAuthProvider = refreshTokenAuthProvider;
  }

  @PostMapping("/login_v2")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDto) {
    var token = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());
    Authentication authentication = daoAuthenticationProvider.authenticate(token);
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

  @PostMapping("/register_v2")
  public ResponseEntity<TokenDTO> register(@RequestBody @Valid ManicUserDTO userDto) {
    ManicUser newUser = userMapper.toManicUser(userDto);
    ManicUser result = userService.createUser(newUser);
    SecurityManicUser securityUser = new SecurityManicUser(result);
    Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(securityUser, userDto.getPassword(), securityUser.getAuthorities());
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDto) {
    Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDto.getRefreshToken()));
    Jwt jwt = (Jwt) authentication.getCredentials();
    String username = jwt.getSubject();
    userService.ensureUserExistByUsername(username);
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

}
