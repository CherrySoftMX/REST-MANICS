package com.cherrysoft.manics.bootstrap;

import com.cherrysoft.manics.exception.v2.user.UsernameAlreadyTakenException;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.auth.ManicUserRole;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "application.admin")
@Setter
@RequiredArgsConstructor
@Profile("dev")
public class AdminAccountInitializer implements ApplicationRunner {
  private final ManicUserService userService;
  private String defaultUsername;
  private String defaultPassword;
  private String defaultEmail;

  @Override
  public void run(ApplicationArguments args) {
    try {
      createDefaultAdminUserIfNotExists();
      printDefaultAdminUserCredentials();
    } catch (UsernameAlreadyTakenException e) {
      printDefaultAdminUserCredentials();
    }
  }

  private void createDefaultAdminUserIfNotExists() {
    if (userService.userExistsByUsername(defaultUsername)) {
      return;
    }
    ManicUser user = new ManicUser();
    user.setUsername(defaultUsername);
    user.setEmail(defaultEmail);
    user.setPassword(defaultPassword);
    user.addRoles(Set.of(ManicUserRole.ADMIN));
    userService.createUser(user);
  }

  private void printDefaultAdminUserCredentials() {
    System.out.printf("Default admin username: %s%n", defaultUsername);
    System.out.printf("Default admin password: %s%n", defaultPassword);
  }

}
