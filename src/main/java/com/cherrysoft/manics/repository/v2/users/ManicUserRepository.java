package com.cherrysoft.manics.repository.v2.users;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManicUserRepository extends JpaRepository<ManicUser, Long> {

  Optional<ManicUser> findByUsername(String username);

}
