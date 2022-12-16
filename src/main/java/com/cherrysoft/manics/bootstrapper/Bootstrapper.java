package com.cherrysoft.manics.bootstrapper;

import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.model.legacy.auth.UserRole;
import com.cherrysoft.manics.service.legacy.user.UserService;
import com.google.common.collect.Sets;
import com.cherrysoft.manics.exception.legacy.UsuarioRegistradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.admin")
public class Bootstrapper implements ApplicationRunner {

  @Autowired
  private UserService userService;

  private String defaultUsername;
  private String defaultPassword;
  private String defaultEmail;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.printf("Default admin username: %s%nDefault admin password: %s%n", defaultUsername, defaultPassword);

    try {
      userService.createUser(
          new User(
              defaultUsername,
              defaultEmail,
              defaultPassword,
              Sets.newHashSet(UserRole.ADMIN)
          )
      );
    } catch (UsuarioRegistradoException e) {

    }
  }

  public void setDefaultUsername(String defaultUsername) {
    this.defaultUsername = defaultUsername;
  }

  public void setDefaultPassword(String defaultPassword) {
    this.defaultPassword = defaultPassword;
  }

  public void setDefaultEmail(String defaultEmail) {
    this.defaultEmail = defaultEmail;
  }
}
