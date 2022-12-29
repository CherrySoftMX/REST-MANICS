package com.cherrysoft.manics.web.controller.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.auth.ManicUserRole;
import com.cherrysoft.manics.model.specs.UpdateUserRolesSpec;
import com.cherrysoft.manics.service.users.ManicUserRoleService;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserRoleSetDTO;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(ManicUserController.BASE_URL)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ManicUserRoleController {
  private final ManicUserRoleService roleService;
  private final ManicUserMapper mapper;

  @PutMapping("/{id}/authorities")
  public ResponseEntity<ManicUserDTO> addUserRoles(
      @PathVariable Long id,
      @RequestBody @Valid ManicUserRoleSetDTO userRoleSet
  ) {
    Set<ManicUserRole> rolesToAdd = userRoleSet.getRoles();
    var roleSpec = new UpdateUserRolesSpec(id, rolesToAdd);
    ManicUser result = roleService.addUserRoles(roleSpec);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}/authorities")
  public ResponseEntity<ManicUserDTO> removeUserRoles(
      @PathVariable Long id,
      @RequestBody @Valid ManicUserRoleSetDTO userRoleSet
  ) {
    Set<ManicUserRole> rolesToRemove = userRoleSet.getRoles();
    var roleSpec = new UpdateUserRolesSpec(id, rolesToRemove);
    ManicUser result = roleService.removeUserRoles(roleSpec);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
