package com.cherrysoft.manics.web.v2.dto.users;

import com.cherrysoft.manics.model.v2.auth.ManicUserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ManicUserRoleSetDTO {
  @NotNull(message = "A user role MUST BE specified.")
  private Set<ManicUserRole> roles = Set.of(ManicUserRole.NORMAL);
}
