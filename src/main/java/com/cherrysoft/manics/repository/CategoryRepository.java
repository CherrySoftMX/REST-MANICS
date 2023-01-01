package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.repository.search.SearchingCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, SearchingCategoryRepository {

  @Query("SELECT c FROM Category c JOIN c.cartoons cartoon WHERE cartoon.id = :cartoonId")
  Page<Category> findAllOfCartoon(Long cartoonId, Pageable pageable);

}
