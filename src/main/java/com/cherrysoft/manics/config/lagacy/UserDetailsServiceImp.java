package com.cherrysoft.manics.config.lagacy;

import com.cherrysoft.manics.exception.legacy.NotFoundException;
import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.repository.legacy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepo.findByUsername(username);

    if (isNull(user)) {
      throw new NotFoundException(String.format("No existe el usuario: %s", username));
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getRoles()
    );
  }

}
