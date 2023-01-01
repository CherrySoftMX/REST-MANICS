package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.repository.search.SearchingCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, SearchingCommentRepository {

  Page<Comment> findCommentsByCartoon_Id(Long cartoonId, Pageable pageable);

  Page<Comment> findCommentsByUser_Id(Long userId, Pageable pageable);

  Page<Comment> findCommentsByParent_Id(Long parentId, Pageable pageable);

}
