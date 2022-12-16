package com.cherrysoft.manics.repository.legacy;

import com.cherrysoft.manics.model.legacy.Suggestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends CrudRepository<Suggestion, Integer> {

}
