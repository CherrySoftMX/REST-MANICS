package com.cherrysoft.manics.service.legacy.user;

import com.cherrysoft.manics.exception.legacy.NotFoundException;
import com.cherrysoft.manics.exception.legacy.UsuarioRegistradoException;
import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.model.legacy.auth.UserRole;
import com.cherrysoft.manics.repository.legacy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;


@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  public List<User> getUsers() {
    return userRepo.findAll();
  }

  public User getUserById(Integer userId) {
    return userRepo
        .findById(userId)
        .orElseThrow(
            () -> new NotFoundException(String.format("No se encontró el usuario con el id: %d", userId))
        );
  }

  public User getUserByUsername(String username) {
    User user = userRepo.findByUsername(username);
    if (isNull(user)) {
      throw new NotFoundException(String.format("No se encontró el usuario con el username: %s", username));
    }
    return user;
  }

  public User createUser(User newUser) {
    User user = userRepo.findByUsername(newUser.getUsername());
    if (!isNull(user)) {
      throw new UsuarioRegistradoException(newUser.getUsername());
    }
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    return userRepo.save(newUser);
  }

  public User saveUser(User user) {
    return userRepo.save(user);
  }

  public User updateUser(Integer userId, User user) {
    return updateUser(userId, user.getUsername(), user.getEmail(), user.getPassword());
  }

  public User updateUser(Integer userId, String newUsername, String newEmail, String newPassword) {
    User user = getUserById(userId);
    user.setUsername(newUsername);
    user.setEmail(newEmail);
    user.setPassword(passwordEncoder.encode(newPassword));
    return userRepo.save(user);
  }

  public User updateUserRoles(Integer userId, Set<UserRole> newUserRoles) {
    User user = getUserById(userId);
    if (!isNull(newUserRoles) && !newUserRoles.isEmpty()) {
      user.setRoles(newUserRoles);
    }
    return userRepo.save(user);
  }

  public User deleteUser(Integer userId) {
    User user = getUserById(userId);
    user.setLikes(null);
    user.setReadLater(null);
    userRepo.deleteById(userId);
    return user;
  }

  public void checkIfUserExist(Integer userId) {
    getUserById(userId);
  }

}
