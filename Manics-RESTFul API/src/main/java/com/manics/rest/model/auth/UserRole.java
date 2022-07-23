package com.manics.rest.model.auth;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum UserRole {

  NORMAL,
  ADMIN;

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    return Sets.newHashSet(
        new SimpleGrantedAuthority(String.format("ROLE_%s", this.name()))
    );
  }

}
