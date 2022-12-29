package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.service.ChapterService;
import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.chapters.ChapterResponseDTO;
import com.cherrysoft.manics.web.dto.pages.PageDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.ChapterMapper;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ChapterController.BASE_URL)
@Tag(name = "Chapters", description = "Manage chapters for a cartoon")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class ChapterController {
  public static final String BASE_URL = "/chapters";
  private final ChapterService chapterService;
  private final ChapterMapper mapper;

  @Operation(summary = "Returns the chapters in which its name matches with the given name")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = ChapterResponseDTO.class)))
  })
  @GetMapping("/search")
  public ResponseEntity<List<ChapterResponseDTO>> searchChaptersByName(
      @RequestParam String name,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    List<Chapter> searchResult = chapterService.searchChapterByName(name, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(searchResult));
  }

  @Operation(summary = "Returns the chapter with the given ID")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = ChapterResponseDTO.class))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ChapterResponseDTO> getChapterById(@PathVariable Long id) {
    Chapter result = chapterService.getChapterById(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @Operation(summary = "Returns the chapter associated with the given cartoon")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = ChapterResponseDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<ChapterResponseDTO>> getCartoonChapters(
      @RequestParam Long cartoonId,
      @PageableDefault @SortDefault(sort = "publicationDate") Pageable pageable
  ) {
    List<Chapter> result = chapterService.getCartoonChapters(cartoonId, pageable);
    return ResponseEntity.ok(mapper.toResponseListDto(result));
  }

  @Operation(summary = "Creates a new chapter for the given chapter")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Chapter created", content = {
          @Content(schema = @Schema(implementation = PageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> createChapter(
      @RequestParam Long cartoonId,
      @RequestBody @Valid ChapterDTO payload
  ) throws URISyntaxException {
    Chapter newChapter = mapper.toChapter(payload);
    Chapter result = chapterService.createChapter(cartoonId, newChapter);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toResponseDto(result));
  }

  @Operation(summary = "Partially updates a chapter with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Chapter updated", content = {
          @Content(schema = @Schema(implementation = ChapterResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> updateChapter(
      @PathVariable Long id,
      @RequestBody @Valid ChapterDTO payload
  ) {
    Chapter updatedChapter = mapper.toChapter(payload);
    Chapter result = chapterService.updateChapter(id, updatedChapter);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  @Operation(summary = "Deletes a chapter with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Chapter deleted", content = {
          @Content(schema = @Schema(implementation = ChapterResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ChapterResponseDTO> deleteChapter(@PathVariable Long id) {
    Chapter result = chapterService.deleteChapter(id);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

}
