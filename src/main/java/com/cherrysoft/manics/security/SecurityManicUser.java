package com.cherrysoft.manics.security;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class SecurityManicUser implements UserDetails {
  private final ManicUser manicUser;

  public SecurityManicUser(String username) {
    this.manicUser = new ManicUser();
    this.manicUser.setUsername(username);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return manicUser.getPassword();
  }

  @Override
  public String getUsername() {
    return manicUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
