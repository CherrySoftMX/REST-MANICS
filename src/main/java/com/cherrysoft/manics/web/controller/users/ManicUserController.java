package com.cherrysoft.manics.web.controller.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.users.ManicUserService;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.hateoas.assemblers.ManicUserModelAssembler;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cherrysoft.manics.util.MediaTypeUtils.APPLICATION_HAL_JSON_VALUE;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ManicUserController.BASE_URL)
public class ManicUserController {
  public static final String BASE_URL = "/users";
  private final ManicUserService userService;
  private final ManicUserMapper mapper;
  private final ManicUserModelAssembler userModelAssembler;

  @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.id == #id")
  public ResponseEntity<ManicUserDTO> getUserById(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id
  ) {
    ManicUser user = userService.getUserById(id);
    return ResponseEntity.ok(userModelAssembler.toModel(user));
  }

  @GetMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.username == #username")
  public ResponseEntity<ManicUserDTO> getUserByUsername(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam String username
  ) {
    ManicUser user = userService.getUserByUsername(username);
    return ResponseEntity.ok(userModelAssembler.toModel(user));
  }

  @PostMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ManicUserDTO> createUser(@RequestBody @Valid ManicUserDTO manicUserDto) {
    ManicUser newUser = mapper.toManicUser(manicUserDto);
    ManicUser result = userService.createUser(newUser);
    return ResponseEntity.ok(userModelAssembler.toModel(result));
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.id == #id")
  public ResponseEntity<ManicUserDTO> updateUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestBody @Valid ManicUserDTO userDto
  ) {
    ManicUser updatedUser = mapper.toManicUser(userDto);
    ManicUser result = userService.updateUser(id, updatedUser);
    return ResponseEntity.ok(userModelAssembler.toModel(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #id")
  public ResponseEntity<ManicUserDTO> deleteUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id
  ) {
    ManicUser result = userService.deleteUser(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
