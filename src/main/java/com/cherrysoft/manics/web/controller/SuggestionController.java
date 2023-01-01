package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.SuggestionService;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import com.cherrysoft.manics.web.hateoas.assemblers.SuggestionModelAssembler;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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
  private final SuggestionModelAssembler suggestionModelAssembler;
  private final PagedResourcesAssembler<Suggestion> suggestionPagedResourcesAssembler;

  @Operation(summary = "Returns the suggestion with the content that matches with the given content")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = SuggestionDTO.class)))
  })
  @GetMapping("/search")
  public PagedModel<SuggestionDTO> searchSuggestion(
      @RequestParam String content,
      @PageableDefault Pageable pageable
  ) {
    Page<Suggestion> searchResult = suggestionService.searchSuggestionByContent(content, pageable);
    return suggestionPagedResourcesAssembler.toModel(searchResult, suggestionModelAssembler);
  }

  @Operation(summary = "Returns the suggestion of the given user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = SuggestionDTO.class)))
  })
  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public PagedModel<SuggestionDTO> getSuggestionsOfUser(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long userId,
      @PageableDefault
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @ParameterObject
      Pageable pageable
  ) {
    Page<Suggestion> result = suggestionService.getSuggestionsOfUser(userId, pageable);
    return suggestionPagedResourcesAssembler.toModel(result, suggestionModelAssembler).withFallbackType(SuggestionDTO.class);
  }

  @Operation(summary = "Returns the suggestion with the given ID")
  @ApiResponse(responseCode = "200", description = "Suggestion found", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public SuggestionDTO getSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId
  ) {
    Suggestion result = suggestionService.getSuggestion(id);
    return suggestionModelAssembler.toModel(result);
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
  ) {
    Suggestion suggestion = mapper.toSuggestion(payload);
    Suggestion result = suggestionService.createSuggestion(userId, suggestion);
    return ResponseEntity
        .created(URI.create(String.format("%s/%s", BASE_URL, result.getId())))
        .body(suggestionModelAssembler.toModel(result));
  }

  @Operation(summary = "Partially updates a suggestion with the given payload")
  @ApiResponse(responseCode = "200", description = "Suggestion updated", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
  @PatchMapping("/{id}")
  @PreAuthorize("#loggedUser.id == #userId")
  public SuggestionDTO updateSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid SuggestionDTO payload
  ) {
    Suggestion updatedSuggestion = mapper.toSuggestion(payload);
    Suggestion result = suggestionService.updateSuggestion(id, updatedSuggestion);
    return suggestionModelAssembler.toModel(result);
  }

  @Operation(summary = "Deletes a suggestion with the given ID")
  @ApiResponse(responseCode = "200", description = "Suggestion deleted", content = {
      @Content(schema = @Schema(implementation = SuggestionDTO.class))
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or #loggedUser.id == #userId")
  public SuggestionDTO deleteSuggestion(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId
  ) {
    Suggestion suggestion = suggestionService.deleteSuggestion(id);
    return mapper.toDto(suggestion);
  }

}
