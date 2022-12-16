package com.cherrysoft.manics.service.legacy;

import com.cherrysoft.manics.exception.NotFoundException;
import com.cherrysoft.manics.model.legacy.Suggestion;
import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.repository.SuggestionRepository;
import com.cherrysoft.manics.repository.UserRepository;
import com.cherrysoft.manics.controller.legacy.request.user.SuggestionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SuggestionService {
  private final SuggestionRepository suggestionRepository;
  private final UserRepository userRepository;

  public List<Suggestion> getSuggestion() {
    List<Suggestion> suggestions = new ArrayList<>();
    suggestionRepository.findAll().iterator().forEachRemaining(suggestions::add);
    return suggestions;
  }

  public Suggestion getSuggestionById(Integer suggestionId) {
    return suggestionRepository
        .findById(suggestionId)
        .orElseThrow(() -> new NotFoundException("No se encontro la sugerencia con Id " + suggestionId));
  }

  public Suggestion createSuggestion(SuggestionRequest req, String username) {
    User user = userRepository.findByUsername(username);
    Suggestion suggestion = new Suggestion(user, req.getContent());
    return suggestionRepository.save(suggestion);
  }

  public Suggestion updateSuggestion(SuggestionRequest req, Integer id) {
    Suggestion suggestion = getSuggestionById(id);
    suggestion.setContent(req.getContent());
    return suggestionRepository.save(suggestion);
  }

  public Suggestion deleteSuggestion(Integer id) {
    Suggestion suggestion = getSuggestionById(id);
    suggestionRepository.delete(suggestion);
    return suggestion;
  }

}
