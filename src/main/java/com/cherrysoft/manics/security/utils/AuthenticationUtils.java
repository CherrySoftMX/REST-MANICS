package com.cherrysoft.manics.security.utils;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.security.SecurityManicUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Objects;

public class AuthenticationUtils {

  public static String getUsername() {
    return getAuthentication().getName();
  }

  public static boolean sameUserAsLogged(ManicUser manicUser) {
    SecurityManicUser securityManicUser = getPrincipal();
    return Objects.equals(securityManicUser.getId(), manicUser.getId());
  }

  public static boolean currentLoggedUserIsAdmin() {
    SecurityManicUser securityManicUser = getPrincipal();
    return securityManicUser.isAdmin();
  }

  @SuppressWarnings("unchecked")
  public static Collection<GrantedAuthority> getAuthorities() {
    return (Collection<GrantedAuthority>) getAuthentication().getAuthorities();
  }

  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public static SecurityManicUser getPrincipal() {
    return (SecurityManicUser) getAuthentication().getPrincipal();
  }

}
