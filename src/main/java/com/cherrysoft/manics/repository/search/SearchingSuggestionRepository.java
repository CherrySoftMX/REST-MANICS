package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchingSuggestionRepository {

  Page<Suggestion> searchSuggestionsByContent(String content, Pageable pageable);

}
