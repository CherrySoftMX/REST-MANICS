package com.cherrysoft.manics.repository.legacy;

import com.cherrysoft.manics.model.legacy.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

}