package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.legacy.Comic;
import com.cherrysoft.manics.model.legacy.core.Category;
import com.cherrysoft.manics.model.legacy.core.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends CrudRepository<Story, Integer> {

  List<Comic> findAllByCategory(Category category);

}
