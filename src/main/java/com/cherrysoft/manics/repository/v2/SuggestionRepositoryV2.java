package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.SuggestionV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepositoryV2 extends JpaRepository<SuggestionV2, Long> {

  @Query("FROM SuggestionV2 s JOIN s.user user WHERE user.id = :userId")
  List<SuggestionV2> findAllSuggestionsOfUser(Long userId, Pageable pageable);

}
