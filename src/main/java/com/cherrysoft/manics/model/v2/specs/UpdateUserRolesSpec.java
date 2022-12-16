package com.cherrysoft.manics.model.v2.specs;

import com.cherrysoft.manics.model.v2.auth.ManicUserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRolesSpec {
  private final Long userId;
  private final Set<ManicUserRole> userRoles;
}
