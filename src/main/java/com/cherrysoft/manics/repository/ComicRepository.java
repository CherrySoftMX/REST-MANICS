package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Comic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends CrudRepository<Comic, Integer> {

  public List<Comic> findByNameLike(String name);

}
