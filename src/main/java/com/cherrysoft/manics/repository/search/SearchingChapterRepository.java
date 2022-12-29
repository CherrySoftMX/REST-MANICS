package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.search.SearchingChapter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchingChapterRepository extends ElasticsearchRepository<SearchingChapter, Long> {

  List<SearchingChapter> findByNameLike(String name, Pageable pageable);

}
