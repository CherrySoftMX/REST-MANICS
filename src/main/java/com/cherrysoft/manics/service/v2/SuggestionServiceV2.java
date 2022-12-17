package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.SuggestionNotFoundException;
import com.cherrysoft.manics.model.v2.SuggestionV2;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.repository.v2.SuggestionRepositoryV2;
import com.cherrysoft.manics.repository.v2.users.ManicUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SuggestionServiceV2 {
  private final SuggestionRepositoryV2 suggestionRepository;
  private final ManicUserRepository userRepository;

  public SuggestionV2 getSuggestion(Long id) {
    return suggestionRepository
        .findById(id)
        .orElseThrow(() -> new SuggestionNotFoundException(id));
  }

  public List<SuggestionV2> getSuggestionsOfUsers(Long userId) {
    return suggestionRepository.findAllSuggestionsOfUser(userId);
  }

  public SuggestionV2 createSuggestion(Long userId, SuggestionV2 newSuggestion) {
    ManicUser userReference = userRepository.getReferenceById(userId);
    newSuggestion.setUser(userReference);
    return suggestionRepository.save(newSuggestion);
  }

  public SuggestionV2 updateSuggestion(Long id, SuggestionV2 updatedSuggestion) {
    SuggestionV2 suggestion = getSuggestion(id);
    suggestion.setContent(updatedSuggestion.getContent());
    return suggestionRepository.save(suggestion);
  }

  public SuggestionV2 deleteSuggestion(Long id) {
    SuggestionV2 suggestion = getSuggestion(id);
    suggestionRepository.deleteById(id);
    return suggestion;
  }

}
