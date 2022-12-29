package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.SuggestionService;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import com.cherrysoft.manics.web.mapper.SuggestionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(SuggestionController.BASE_URL)
@Tag(name = "Suggestions", description = "Manage application suggestions")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class SuggestionController {
  public static final String BASE_URL = "/suggestions";
  private final SuggestionService suggestionService;
  private final SuggestionMapper mapper;

  @Operation(summary = "Returns the suggestion of the given user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = SuggestionDTO.class)))
  })
  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public ResponseEntity<List<SuggestionDTO>> getSuggestionsOfUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long userId,
      @PageableDefault
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @ParameterObject
      Pageable pageable
  ) {
    List<Suggestion> result = suggestionService.getSuggestionsOfUser(userId, pageable);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @Operation(summary = "Returns the suggestion with the given ID")
  @ApiResponse(responseCode = "200", description = "Suggestion found", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
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

  @Operation(summary = "Creates a new suggestion for the given user")
  @ApiResponse(responseCode = "201", description = "Suggestion created", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> createSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO payload
  ) throws URISyntaxException {
    Suggestion suggestion = mapper.toSuggestion(payload);
    Suggestion result = suggestionService.createSuggestion(userId, suggestion);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toDto(result));
  }

  @Operation(summary = "Partially updates a suggestion with the given payload")
  @ApiResponse(responseCode = "200", description = "Suggestion updated", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
  @PatchMapping("/{id}")
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<SuggestionDTO> updateSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO payload
  ) {
    Suggestion updatedSuggestion = mapper.toSuggestion(payload);
    Suggestion result = suggestionService.updateSuggestion(id, updatedSuggestion);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @Operation(summary = "Deletes a suggestion with the given ID")
  @ApiResponse(responseCode = "200", description = "Suggestion deleted", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
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
