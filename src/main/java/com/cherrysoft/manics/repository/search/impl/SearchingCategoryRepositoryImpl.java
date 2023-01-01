package com.cherrysoft.manics.repository.search.impl;

import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.repository.search.SearchingCategoryRepository;
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
public class SearchingCategoryRepositoryImpl implements SearchingCategoryRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Page<Category> searchCategory(String query, Pageable pageable) {
    SearchSession searchSession = Search.session(em);
    SearchResult<Category> result = searchSession.search(Category.class)
        .where(f -> f.match()
            .field("name").boost(2F)
            .field("description")
            .matching(query))
        .fetch((int) pageable.getOffset(), pageable.getPageSize());
    long totalHitCount = result.total().hitCount();
    List<Category> matchingCategories = result.hits();
    return new PageImpl<>(matchingCategories, pageable, totalHitCount);
  }

}
