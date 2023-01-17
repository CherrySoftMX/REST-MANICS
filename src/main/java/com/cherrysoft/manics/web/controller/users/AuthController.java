package com.cherrysoft.manics.web.controller.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.security.TokenGenerator;
import com.cherrysoft.manics.service.users.ManicUserService;
import com.cherrysoft.manics.web.dto.auth.LoginDTO;
import com.cherrysoft.manics.web.dto.auth.TokenDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.cherrysoft.manics.util.ApiDocsConstants.BAD_REQUEST_RESPONSE_REF;
import static com.cherrysoft.manics.util.ApiDocsConstants.NOT_FOUND_RESPONSE_REF;

@RestController
@Validated
@Tag(name = "Auth")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
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

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDto) {
    var token = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());
    Authentication authentication = daoAuthenticationProvider.authenticate(token);
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

  @PostMapping("/register")
  @Validated(OnCreate.class)
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
