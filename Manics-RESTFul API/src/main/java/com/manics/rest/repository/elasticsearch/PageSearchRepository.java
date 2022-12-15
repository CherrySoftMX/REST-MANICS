package com.manics.rest.repository.elasticsearch;

import com.manics.rest.model.core.elasticsearch.PageSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageSearchRepository extends ElasticsearchRepository<PageSearch, Integer> {

}
