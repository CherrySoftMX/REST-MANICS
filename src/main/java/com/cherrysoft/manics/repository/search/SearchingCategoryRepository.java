package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchingCategoryRepository {

  Page<Category> searchCategory(String query, Pageable pageable);

}
