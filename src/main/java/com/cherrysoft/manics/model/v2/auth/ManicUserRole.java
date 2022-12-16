package com.cherrysoft.manics.model.v2.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum ManicUserRole {
  NORMAL,
  ADMIN;

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    return Set.of(
        new SimpleGrantedAuthority(String.format("ROLE_%s", this.name()))
    );
  }

}
