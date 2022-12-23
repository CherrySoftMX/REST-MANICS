package com.cherrysoft.manics.repository.v2.search;

import com.cherrysoft.manics.model.v2.search.SearchingPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchingPageRepository extends ElasticsearchRepository<SearchingPage, Long> {

}
