package com.cherrysoft.manics.repository.v2.search;

import com.cherrysoft.manics.model.v2.search.SearchingCartoon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchingCartoonRepository extends ElasticsearchRepository<SearchingCartoon, Long> {

  List<SearchingCartoon> findByNameLike(String name, Pageable pageable);

}
