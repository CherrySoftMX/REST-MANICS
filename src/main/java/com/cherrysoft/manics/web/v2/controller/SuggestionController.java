package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.web.v2.dto.SuggestionDTO;
import com.cherrysoft.manics.mappers.v2.SuggestionMapper;
import com.cherrysoft.manics.model.v2.SuggestionV2;
import com.cherrysoft.manics.service.v2.SuggestionServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(SuggestionController.BASE_URL)
public class SuggestionController {
  public static final String BASE_URL = "/suggestions";
  private final SuggestionServiceV2 suggestionService;
  private final SuggestionMapper mapper;

  @GetMapping
  public ResponseEntity<List<SuggestionDTO>> getSuggestionOfUser(@RequestParam Long userId) {
    List<SuggestionV2> result = suggestionService.getSuggestionsOfUsers(userId);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SuggestionDTO> getSuggestion(@PathVariable Long id) {
    SuggestionV2 suggestion = suggestionService.getSuggestion(id);
    return ResponseEntity.ok(mapper.toDto(suggestion));
  }

  @PostMapping
  public ResponseEntity<SuggestionDTO> createSuggestion(
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO suggestionDto
  ) {
    SuggestionV2 suggestion = mapper.toSuggestion(suggestionDto);
    SuggestionV2 result = suggestionService.createSuggestion(userId, suggestion);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SuggestionDTO> updateSuggestion(
      @PathVariable Long id,
      @RequestBody @Valid SuggestionDTO suggestionDto
  ) {
    SuggestionV2 updatedSuggestion = mapper.toSuggestion(suggestionDto);
    SuggestionV2 result = suggestionService.updateSuggestion(id, updatedSuggestion);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SuggestionDTO> deleteSuggestion(@PathVariable Long id) {
    SuggestionV2 suggestion = suggestionService.deleteSuggestion(id);
    return ResponseEntity.ok(mapper.toDto(suggestion));
  }

}
