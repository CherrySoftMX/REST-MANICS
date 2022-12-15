package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Manga;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends CrudRepository<Manga, Integer> {
  public List<Manga> findByNameLike(String name);
}
