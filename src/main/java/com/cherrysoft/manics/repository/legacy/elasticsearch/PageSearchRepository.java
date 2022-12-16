package com.cherrysoft.manics.repository.legacy.elasticsearch;

import com.cherrysoft.manics.model.legacy.core.elasticsearch.PageSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageSearchRepository extends ElasticsearchRepository<PageSearch, Integer> {

}
