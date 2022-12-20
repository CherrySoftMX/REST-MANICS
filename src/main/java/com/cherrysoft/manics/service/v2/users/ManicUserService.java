package com.cherrysoft.manics.service.v2.users;

import com.cherrysoft.manics.exception.v2.user.UserNotFoundException;
import com.cherrysoft.manics.exception.v2.user.UsernameAlreadyTakenException;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.auth.ManicUserRole;
import com.cherrysoft.manics.repository.v2.users.ManicUserRepository;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ManicUserService {
  private final ManicUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public ManicUser getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
  }

  public ManicUser getUserReferenceById(Long id) {
    return userRepository.getReferenceById(id);
  }

  public ManicUser getUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  public void ensureUserExistByUsername(String username) {
    if (!userRepository.existsByUsername(username)) {
      throw new UserNotFoundException(username);
    }
  }

  public ManicUser createUser(ManicUser newUser) {
    ensureUniqueUsername(newUser.getUsername());
    newUser.addRoles(Set.of(ManicUserRole.NORMAL));
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    return userRepository.save(newUser);
  }

  ManicUser saveUser(ManicUser user) {
    return userRepository.save(user);
  }

  public ManicUser updateUser(Long id, ManicUser updatedUser) {
    ManicUser user = getUserById(id);
    BeanUtils.copyProperties(updatedUser, user);
    if (nonNull(updatedUser.getPassword())) {
      user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }
    return userRepository.save(user);
  }

  public ManicUser deleteUser(Long id) {
    ManicUser user = getUserById(id);
    userRepository.deleteById(id);
    return user;
  }

  private void ensureUniqueUsername(String username) {
    Optional<ManicUser> userMaybe = userRepository.findByUsername(username);
    if (userMaybe.isPresent()) {
      throw new UsernameAlreadyTakenException(username);
    }
  }

}
