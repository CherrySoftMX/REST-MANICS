package com.cherrysoft.manics.repository.elasticsearch;

import com.cherrysoft.manics.model.core.elasticsearch.StorySearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorySearchRepository extends ElasticsearchRepository<StorySearch, Integer> {
  public List<StorySearch> findByNameLike(String name);
}
