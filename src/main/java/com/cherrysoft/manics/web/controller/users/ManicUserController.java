package com.cherrysoft.manics.web.controller.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.users.ManicUserService;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.hateoas.assemblers.ManicUserModelAssembler;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;
import static com.cherrysoft.manics.util.MediaTypeUtils.APPLICATION_HAL_JSON_VALUE;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(ManicUserController.BASE_URL)
@Tag(name = "Users", description = "Manage manics users")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class ManicUserController {
  public static final String BASE_URL = "/users";
  private final ManicUserService userService;
  private final ManicUserMapper mapper;
  private final ManicUserModelAssembler userModelAssembler;

  @Operation(summary = "Returns the user with the given ID")
  @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.id == #id")
  public ManicUserDTO getUserById(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id
  ) {
    ManicUser user = userService.getUserById(id);
    return userModelAssembler.toModel(user);
  }

  @Operation(summary = "Returns the user with the given username")
  @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @GetMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.username == #username")
  public ManicUserDTO getUserByUsername(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam String username
  ) {
    ManicUser user = userService.getUserByUsername(username);
    return userModelAssembler.toModel(user);
  }

  @Operation(summary = "Creates a new user with the given payload")
  @ApiResponse(responseCode = "201", description = "User created", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @PostMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ManicUserDTO> createUser(@RequestBody @Valid ManicUserDTO payload) throws URISyntaxException {
    ManicUser newUser = mapper.toManicUser(payload);
    ManicUser result = userService.createUser(newUser);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toDto(result));
  }

  @Operation(summary = "Partially updates a user with the given payload")
  @ApiResponse(responseCode = "200", description = "User updated", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @PatchMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("#loggedUser.id == #id")
  public ManicUserDTO updateUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestBody @Valid ManicUserDTO payload
  ) {
    ManicUser updatedUser = mapper.toManicUser(payload);
    ManicUser result = userService.updateUser(id, updatedUser);
    return userModelAssembler.toModel(result);
  }

  @Operation(summary = "Deletes a user with the given ID")
  @ApiResponse(responseCode = "200", description = "User deleted", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #id")
  public ManicUserDTO deleteUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id
  ) {
    ManicUser result = userService.deleteUser(id);
    return mapper.toDto(result);
  }

}
