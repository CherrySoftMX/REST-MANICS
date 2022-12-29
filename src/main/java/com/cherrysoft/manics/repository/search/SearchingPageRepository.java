package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.search.SearchingPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchingPageRepository extends ElasticsearchRepository<SearchingPage, Long> {

}
