package com.manics.rest.repository;

import com.manics.rest.model.Comic;
import com.manics.rest.model.core.Category;
import com.manics.rest.model.core.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends CrudRepository<Story, Integer> {

  List<Comic> findAllByCategory(Category category);

}
