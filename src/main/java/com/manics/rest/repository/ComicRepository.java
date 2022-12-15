package com.manics.rest.repository;

import com.manics.rest.model.Comic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends CrudRepository<Comic, Integer> {

  public List<Comic> findByNameLike(String name);

}
