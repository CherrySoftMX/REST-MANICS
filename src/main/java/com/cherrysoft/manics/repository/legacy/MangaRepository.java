package com.cherrysoft.manics.repository.legacy;

import com.cherrysoft.manics.model.legacy.Manga;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends CrudRepository<Manga, Integer> {
  public List<Manga> findByNameLike(String name);
}
