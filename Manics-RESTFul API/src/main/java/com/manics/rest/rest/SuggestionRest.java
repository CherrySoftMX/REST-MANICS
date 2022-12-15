package com.manics.rest.rest;

import com.manics.rest.model.Suggestion;
import com.manics.rest.rest.request.user.SuggestionRequest;
import com.manics.rest.service.SuggestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
public class SuggestionRest {
  private final SuggestionService suggestionService;

  @GetMapping("/sugerencias")
  public ResponseEntity<List<Suggestion>> getSugestions() {
    return ResponseEntity.ok().body(suggestionService.getSuggestion());
  }

  @GetMapping("/sugerencias/{id}")
  public ResponseEntity<Suggestion> getSuggestionById(@PathVariable(name = "id") Integer id) {
    Suggestion suggestion = suggestionService.getSuggestionById(id);
    return ResponseEntity.ok().body(suggestion);
  }

  @PostMapping("/sugerencias/{username}")
  public ResponseEntity<Suggestion> createSuggestion(
      @RequestBody @Valid SuggestionRequest request,
      @PathVariable String username
  ) throws URISyntaxException {
    Suggestion suggestion = suggestionService.createSuggestion(request, username);
    return ResponseEntity.created(new URI("/sugerencias/" + suggestion.getId())).body(suggestion);
  }

  @PutMapping("/sugerencias/{id}")
  public ResponseEntity<Suggestion> updateSuggestion(
      @PathVariable Integer id,
      @RequestBody @Valid SuggestionRequest request
  ) throws URISyntaxException {
    Suggestion suggestion = suggestionService.updateSuggestion(request, id);
    return ResponseEntity
        .created(
            new URI("sugerencias/" + suggestion.getId())
        ).body(suggestion);
  }

  @DeleteMapping("/sugerencias/{id}")
  public ResponseEntity<Suggestion> deleteSuggestion(@PathVariable(name = "id") Integer id) {
    Suggestion suggestion = suggestionService.deleteSuggestion(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(suggestion);
  }

}