package com.cherrysoft.manics.controller.v2.users;

import com.cherrysoft.manics.controller.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.controller.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.controller.v2.hateoas.assemblers.ManicUserModelAssembler;
import com.cherrysoft.manics.mappers.v2.ManicUserMapper;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ManicUserDTO> getUserById(@PathVariable Long id) {
    ManicUser user = userService.getUserById(id);
    return ResponseEntity.ok(userModelAssembler.toModel(user));
  }

  @GetMapping(produces = APPLICATION_HAL_JSON_VALUE)
  public ResponseEntity<ManicUserDTO> getUserByUsername(@RequestParam String username) {
    ManicUser user = userService.getUserByUsername(username);
    return ResponseEntity.ok(userModelAssembler.toModel(user));
  }

  @PostMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @Validated(OnCreate.class)
  public ResponseEntity<ManicUserDTO> createUser(@RequestBody @Valid ManicUserDTO manicUserDto) {
    ManicUser newUser = mapper.toManicUser(manicUserDto);
    ManicUser result = userService.createUser(newUser);
    return ResponseEntity.ok(userModelAssembler.toModel(result));
  }

  @PatchMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  public ResponseEntity<ManicUserDTO> updateUser(
      @PathVariable Long id,
      @RequestBody @Valid ManicUserDTO userDto
  ) {
    ManicUser updatedUser = mapper.toManicUser(userDto);
    ManicUser result = userService.updateUser(id, updatedUser);
    return ResponseEntity.ok(userModelAssembler.toModel(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ManicUserDTO> deleteUser(@PathVariable Long id) {
    ManicUser result = userService.deleteUser(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
