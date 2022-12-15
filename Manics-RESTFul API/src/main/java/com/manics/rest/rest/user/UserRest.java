package com.manics.rest.rest.user;

import com.manics.rest.mappers.UserMapper;
import com.manics.rest.model.auth.User;
import com.manics.rest.model.core.Story;
import com.manics.rest.rest.request.user.UserAuthorityRequest;
import com.manics.rest.rest.request.user.UserRequest;
import com.manics.rest.service.stories.StoryService;
import com.manics.rest.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UserRest {
  private final UserService userService;
  private final StoryService storyService;
  private final UserMapper userMapper;

  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok().body(userService.getUsers());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
    User user = userService.getUserById(userId);
    return ResponseEntity.ok().body(user);
  }

  @GetMapping("/{userId}/read-later")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Set<Story>> getReadLater(@PathVariable Integer userId) {
    return ResponseEntity.ok().body(storyService.getReadLater(userId));
  }

  @GetMapping("/self/read-later")
  public ResponseEntity<Set<Story>> getReadLater(Principal principal) {
    return ResponseEntity.ok().body(storyService.getReadLater(principal.getName()));
  }

  @PutMapping("/{userId}")
  public ResponseEntity<User> updateUser(
      @PathVariable Integer userId,
      @RequestBody UserRequest request
  ) {
    User user = userService.updateUser(userId, userMapper.userRequestToUser(request));
    return ResponseEntity.ok(user);
  }

  @PutMapping("/authorities/{userId}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<User> updateUserAuthorities(
      @PathVariable Integer userId,
      @RequestBody @Valid UserAuthorityRequest userAuthorityRequest
  ) {

    User user = userService.updateUserRoles(userId, userAuthorityRequest.getRoles());
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<User> deleteUser(@PathVariable Integer userId) {
    User user = userService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
  }

}
