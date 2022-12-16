package com.cherrysoft.manics.controller.request.user;

import com.google.common.collect.Sets;
import com.cherrysoft.manics.model.legacy.auth.UserRole;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserAuthorityRequest {

  /**
   * {
   * "roles": [
   * <p>
   * "ADMIN" || "NORMAL"
   * <p>
   * ]
   * }
   */
  @NotNull(message = "Se debe especificar el nuevo rol del usuario.")
  private Set<UserRole> roles = Sets.newHashSet(UserRole.NORMAL);

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

}
