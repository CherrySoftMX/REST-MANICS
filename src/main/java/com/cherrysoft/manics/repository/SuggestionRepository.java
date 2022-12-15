package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Suggestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends CrudRepository<Suggestion, Integer> {

}
