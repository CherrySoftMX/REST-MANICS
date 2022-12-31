package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.SuggestionNotFoundException;
import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.repository.SuggestionRepository;
import com.cherrysoft.manics.repository.users.ManicUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SuggestionService {
  private final SuggestionRepository suggestionRepository;
  private final ManicUserRepository userRepository;

  public Page<Suggestion> searchSuggestionByContent(String content, Pageable pageable) {
    return suggestionRepository.searchSuggestionsByContent(content, pageable);
  }

  public Suggestion getSuggestion(Long id) {
    return suggestionRepository
        .findById(id)
        .orElseThrow(() -> new SuggestionNotFoundException(id));
  }

  public Page<Suggestion> getSuggestionsOfUser(Long userId, Pageable pageable) {
    return suggestionRepository.findSuggestionsByUser_Id(userId, pageable);
  }

  public Suggestion createSuggestion(Long userId, Suggestion newSuggestion) {
    ManicUser userReference = userRepository.getReferenceById(userId);
    newSuggestion.setUser(userReference);
    return suggestionRepository.save(newSuggestion);
  }

  public Suggestion updateSuggestion(Long id, Suggestion updatedSuggestion) {
    Suggestion suggestion = getSuggestion(id);
    suggestion.setContent(updatedSuggestion.getContent());
    return suggestionRepository.save(suggestion);
  }

  public Suggestion deleteSuggestion(Long id) {
    Suggestion suggestion = getSuggestion(id);
    suggestionRepository.deleteById(id);
    return suggestion;
  }

}
