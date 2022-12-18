package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartoonRepository extends JpaRepository<Cartoon, Long> {

  Optional<Cartoon> findCartoonByIdAndType(Long id, CartoonType type);

  List<Cartoon> findCartoonsByType(CartoonType type);

  @Query("SELECT COUNT(c) > 0 FROM Cartoon c JOIN c.likedBy user WHERE c.id = :cartoonId AND user.id = :userId")
  boolean isCartoonLikedBy(Long cartoonId, Long userId);

  @Query("FROM Cartoon c JOIN c.likedBy user WHERE user.id = :userId")
  List<Cartoon> getLikes(Long userId);

}
