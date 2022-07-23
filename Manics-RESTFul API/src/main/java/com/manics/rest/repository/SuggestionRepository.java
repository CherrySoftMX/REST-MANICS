package com.manics.rest.repository;

import com.manics.rest.model.Suggestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends CrudRepository<Suggestion, Integer> {

}
