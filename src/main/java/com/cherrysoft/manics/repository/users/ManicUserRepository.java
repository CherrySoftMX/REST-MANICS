package com.cherrysoft.manics.repository.users;

import com.cherrysoft.manics.model.auth.ManicUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManicUserRepository extends JpaRepository<ManicUser, Long> {

  Optional<ManicUser> findByUsername(String username);

  boolean existsByUsername(String username);

  @Query("SELECT m FROM ManicUser m JOIN m.likes cartoon WHERE cartoon.id = :cartoonId")
  Page<ManicUser> getLikedBy(Long cartoonId, Pageable pageable);

}
