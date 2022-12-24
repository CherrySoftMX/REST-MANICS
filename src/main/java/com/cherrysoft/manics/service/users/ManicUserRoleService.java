package com.cherrysoft.manics.service.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.UpdateUserRolesSpec;
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
