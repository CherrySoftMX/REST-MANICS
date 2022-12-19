package com.cherrysoft.manics.web.legacy.user;

import com.cherrysoft.manics.mappers.legacy.UserMapper;
import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.web.legacy.request.user.UserRequest;
import com.cherrysoft.manics.service.legacy.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@AllArgsConstructor
public class AuthRest {
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok().build();
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody @Valid UserRequest request) throws URISyntaxException {
    User user = userService.createUser(userMapper.userRequestToUser(request));
    return ResponseEntity
        .created(
            new URI("/usuarios/" + user.getUserId())
        ).body(user);
  }

}
