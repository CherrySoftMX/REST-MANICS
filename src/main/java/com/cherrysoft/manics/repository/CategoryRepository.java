package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.core.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
