package com.cherrysoft.manics.web.controller.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.auth.ManicUserRole;
import com.cherrysoft.manics.model.specs.UpdateUserRolesSpec;
import com.cherrysoft.manics.service.users.ManicUserRoleService;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserRoleSetDTO;
import com.cherrysoft.manics.web.hateoas.assemblers.ManicUserModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ManicUserController.BASE_URL)
@Tag(name = "Roles", description = "Manage user roles")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class ManicUserRoleController {
  private final ManicUserRoleService roleService;
  private final ManicUserModelAssembler manicUserModelAssembler;

  @Operation(summary = "Adds the provided roles to the specified user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @PutMapping("/{id}/authorities")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ManicUserDTO addUserRoles(
      @PathVariable Long id,
      @RequestBody @Valid ManicUserRoleSetDTO payload
  ) {
    Set<ManicUserRole> rolesToAdd = payload.getRoles();
    var roleSpec = new UpdateUserRolesSpec(id, rolesToAdd);
    ManicUser result = roleService.addUserRoles(roleSpec);
    return manicUserModelAssembler.toModel(result);
  }

  @Operation(summary = "Removes the provided roles to the specified user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = ManicUserDTO.class))
  })
  @DeleteMapping("/{id}/authorities")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ManicUserDTO removeUserRoles(
      @PathVariable Long id,
      @RequestBody @Valid ManicUserRoleSetDTO payload
  ) {
    Set<ManicUserRole> rolesToRemove = payload.getRoles();
    var roleSpec = new UpdateUserRolesSpec(id, rolesToRemove);
    ManicUser result = roleService.removeUserRoles(roleSpec);
    return manicUserModelAssembler.toModel(result);
  }

}
