package com.cherrysoft.manics.repository.legacy;

import com.cherrysoft.manics.model.legacy.core.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}