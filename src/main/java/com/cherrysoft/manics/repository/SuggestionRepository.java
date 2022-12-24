package com.cherrysoft.manics.repository;

import com.cherrysoft.manics.model.Suggestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

  @Query("FROM Suggestion s JOIN s.user user WHERE user.id = :userId")
  List<Suggestion> findAllSuggestionsOfUser(Long userId, Pageable pageable);

}
