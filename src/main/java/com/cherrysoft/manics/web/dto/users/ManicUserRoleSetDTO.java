package com.cherrysoft.manics.web.dto.users;

import com.cherrysoft.manics.model.auth.ManicUserRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ManicUserRoleSetDTO {
  @NotNull(message = "A user role MUST BE specified.")
  private Set<ManicUserRole> roles = Set.of(ManicUserRole.NORMAL);
}
