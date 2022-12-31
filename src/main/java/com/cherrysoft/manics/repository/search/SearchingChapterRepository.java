package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchingChapterRepository {

  Page<Chapter> searchChapterByName(String name, Pageable pageable);

}
