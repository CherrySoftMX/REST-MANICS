package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CartoonRepository extends JpaRepository<Cartoon, Long> {

  Page<Cartoon> findAllByIdIn(Collection<Long> ids, Pageable pageable);

  Optional<Cartoon> findCartoonByIdAndType(Long id, CartoonType type);

  Page<Cartoon> findCartoonsByType(CartoonType type, Pageable pageable);

  @Query("SELECT COUNT(c) > 0 FROM Cartoon c JOIN c.likedBy user WHERE c.id = :cartoonId AND user.id = :userId")
  boolean isCartoonLikedBy(Long cartoonId, Long userId);

  @Query("SELECT COUNT(c) > 0 FROM Cartoon c JOIN c.bookmarkedBy user WHERE c.id = :cartoonId AND user.id = :userId")
  boolean isCartoonBookmarkedBy(Long cartoonId, Long userId);

  @Query("SELECT c FROM Cartoon c JOIN c.likedBy user WHERE user.id = :userId")
  Page<Cartoon> getLikes(Long userId, Pageable pageable);

  @Query("SELECT c FROM Cartoon c JOIN c.bookmarkedBy user WHERE user.id = :userId")
  Page<Cartoon> getBookmarks(Long userId, Pageable pageable);

}
