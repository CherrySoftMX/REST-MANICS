package com.cherrysoft.manics.model.specs;

import com.cherrysoft.manics.model.auth.ManicUserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRolesSpec {
  private final Long userId;
  private final Set<ManicUserRole> userRoles;
}
