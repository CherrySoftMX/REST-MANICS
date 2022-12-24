package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.SuggestionService;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import com.cherrysoft.manics.web.mapper.SuggestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(SuggestionController.BASE_URL)
public class SuggestionController {
  public static final String BASE_URL = "/suggestions";
  private final SuggestionService suggestionService;
  private final SuggestionMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public ResponseEntity<List<SuggestionDTO>> getSuggestionsOfUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long userId,
      @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    List<Suggestion> result = suggestionService.getSuggestionsOfUser(userId, pageable);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> getSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId
  ) {
    Suggestion suggestion = suggestionService.getSuggestion(id);
    return ResponseEntity.ok(mapper.toDto(suggestion));
  }

  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> createSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO suggestionDto
  ) {
    Suggestion suggestion = mapper.toSuggestion(suggestionDto);
    Suggestion result = suggestionService.createSuggestion(userId, suggestion);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> updateSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO suggestionDto
  ) {
    Suggestion updatedSuggestion = mapper.toSuggestion(suggestionDto);
    Suggestion result = suggestionService.updateSuggestion(id, updatedSuggestion);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> deleteSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId
  ) {
    Suggestion suggestion = suggestionService.deleteSuggestion(id);
    return ResponseEntity.ok(mapper.toDto(suggestion));
  }

}
