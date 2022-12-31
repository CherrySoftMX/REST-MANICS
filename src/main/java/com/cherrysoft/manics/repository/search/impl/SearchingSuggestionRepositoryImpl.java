package com.cherrysoft.manics.repository.search.impl;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.repository.search.SearchingSuggestionRepository;
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
public class SearchingSuggestionRepositoryImpl implements SearchingSuggestionRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Suggestion> searchSuggestionsByContent(String content, Pageable pageable) {
    SearchSession searchSession = Search.session(em);
    SearchResult<Suggestion> result = searchSession.search(Suggestion.class)
        .where(f -> f.match()
            .fields("content")
            .matching(content))
        .fetch((int) pageable.getOffset(), pageable.getPageSize());
    long totalHitCount = result.total().hitCount();
    List<Suggestion> matchingSuggestions = result.hits();
    return new PageImpl<>(matchingSuggestions, pageable, totalHitCount);
  }

}
