package com.cherrysoft.manics.repository.search.impl;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.repository.search.SearchingChapterRepository;
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
public class SearchingChapterRepositoryImpl implements SearchingChapterRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Chapter> searchChapterByName(String name, Pageable pageable) {
    SearchSession searchSession = Search.session(em);
    SearchResult<Chapter> result = searchSession.search(Chapter.class)
        .where(f -> f.match()
            .field("name")
            .matching(name))
        .fetch((int) pageable.getOffset(), pageable.getPageSize());
    long totalHitCount = result.total().hitCount();
    List<Chapter> matchingChapters = result.hits();
    return new PageImpl<>(matchingChapters, pageable, totalHitCount);
  }

}
