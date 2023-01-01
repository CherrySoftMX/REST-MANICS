package com.cherrysoft.manics.repository.search.impl;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.repository.search.SearchingCartoonRepository;
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
public class SearchingCartoonRepositoryImpl implements SearchingCartoonRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Cartoon> searchCartoons(String query, Pageable pageable) {
    SearchSession searchSession = Search.session(em);
    SearchResult<Cartoon> result = searchSession.search(Cartoon.class)
        .where(f -> f.match()
            .field("name").boost(2F)
            .field("chapters.name")
            .matching(query))
        .fetch((int) pageable.getOffset(), pageable.getPageSize());
    long totalHitCount = result.total().hitCount();
    List<Cartoon> matchingCartoons = result.hits();
    return new PageImpl<>(matchingCartoons, pageable, totalHitCount);
  }

}
