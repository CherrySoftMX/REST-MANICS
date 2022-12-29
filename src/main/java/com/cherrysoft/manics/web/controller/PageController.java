package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Page;
import com.cherrysoft.manics.service.PageService;
import com.cherrysoft.manics.web.dto.pages.PageDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.PageMapper;
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
@RequestMapping(PageController.BASE_URL)
@Tag(name = "Pages", description = "Manage pages for a chapter")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class PageController {
  public static final String BASE_URL = "/pages";
  private final PageService pageService;
  private final PageMapper mapper;

  @Operation(summary = "Returns the page with the given ID")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = PageDTO.class))
  })
  @GetMapping("/{id}")
  public ResponseEntity<PageDTO> getPageById(@PathVariable Long id) {
    Page result = pageService.getPageById(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @Operation(summary = "Returns the pages associated with the given chapter")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = PageDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<PageDTO>> getChapterPages(
      @RequestParam Long chapterId,
      @PageableDefault
      @SortDefault(sort = "pageNumber")
      @ParameterObject
      Pageable pageable
  ) {
    List<Page> result = pageService.getChapterPages(chapterId, pageable);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @Operation(summary = "Creates a new page for the given chapter")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Page created", content = {
          @Content(schema = @Schema(implementation = PageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<PageDTO> createPage(
      @RequestParam Long chapterId,
      @RequestBody @Valid PageDTO payload
  ) throws URISyntaxException {
    Page newPage = mapper.toPage(payload);
    Page result = pageService.createPage(chapterId, newPage);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toDto(result));
  }

  @Operation(summary = "Partially updates a page with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Page updated", content = {
          @Content(schema = @Schema(implementation = PageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<PageDTO> updatePage(
      @PathVariable Long id,
      @RequestBody @Valid PageDTO payload
  ) {
    Page updatedPage = mapper.toPage(payload);
    Page result = pageService.updatePage(id, updatedPage);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @Operation(summary = "Deletes a page with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Page deleted", content = {
          @Content(schema = @Schema(implementation = PageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<PageDTO> deletePage(@PathVariable Long id) {
    Page result = pageService.deletePage(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
