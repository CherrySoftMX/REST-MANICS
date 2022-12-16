package com.cherrysoft.manics.service.v2.users;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.UpdateUserRolesSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManicUserRoleService {
  private final ManicUserService userService;

  public ManicUser addUserRoles(UpdateUserRolesSpec spec) {
    ManicUser user = userService.getUserById(spec.getUserId());
    user.addRoles(spec.getUserRoles());
    return userService.saveUser(user);
  }

  public ManicUser removeUserRoles(UpdateUserRolesSpec spec) {
    ManicUser user = userService.getUserById(spec.getUserId());
    user.removeRoles(spec.getUserRoles());
    return userService.saveUser(user);
  }

}
