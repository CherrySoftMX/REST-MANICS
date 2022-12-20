package com.cherrysoft.manics.security;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class SecurityManicUser implements UserDetails {
  private final ManicUser manicUser;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return manicUser.getRoles().stream()
        .map(Enum::name)
        .map(role -> "ROLE_" + role)
        .map(SimpleGrantedAuthority::new)
        .collect(toList());
  }

  public Long getId() {
    return manicUser.getId();
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
