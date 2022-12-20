package com.cherrysoft.manics.security.service;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManicUserDetailsService implements UserDetailsService {
  private final ManicUserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      ManicUser manicUser = userService.getUserByUsername(username);
      return new SecurityManicUser(manicUser);
    } catch (UsernameNotFoundException e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }

}
