package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

  Page<Suggestion> findSuggestionsByUser_Id(Long userId, Pageable pageable);

}
