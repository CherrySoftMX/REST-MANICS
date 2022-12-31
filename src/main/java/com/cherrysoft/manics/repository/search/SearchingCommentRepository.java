package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchingCommentRepository {

  Page<Comment> searchCommentByContent(String content, Pageable pageable);

}
