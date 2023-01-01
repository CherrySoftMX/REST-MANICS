package com.cherrysoft.manics.repository.search;

import com.cherrysoft.manics.model.Cartoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchingCartoonRepository {

  Page<Cartoon> searchCartoons(String query, Pageable pageable);

}
