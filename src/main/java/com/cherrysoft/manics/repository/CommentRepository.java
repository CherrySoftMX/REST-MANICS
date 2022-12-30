package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  Page<Comment> findCommentsByCartoon_Id(Long cartoonId, Pageable pageable);

  Page<Comment> findCommentsByUser_Id(Long userId, Pageable pageable);

}
