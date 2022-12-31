package com.cherrysoft.manics.repository.search.impl;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.repository.search.SearchingCommentRepository;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class SearchingCommentRepositoryImpl implements SearchingCommentRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Comment> searchCommentByContent(String content, Pageable pageable) {
    SearchSession searchSession = Search.session(em);
    SearchResult<Comment> result = searchSession.search(Comment.class)
        .where(f -> f.match()
            .fields("content")
            .matching(content))
        .fetch((int) pageable.getOffset(), pageable.getPageSize());
    long totalHitCount = result.total().hitCount();
    List<Comment> matchingComments = result.hits();
    return new PageImpl<>(matchingComments, pageable, totalHitCount);
  }

}
