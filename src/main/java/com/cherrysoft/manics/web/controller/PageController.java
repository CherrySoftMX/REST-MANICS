package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.service.PageService;
import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.hateoas.assemblers.PageModelAssembler;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;
import static com.cherrysoft.manics.util.MediaTypeUtils.APPLICATION_HAL_JSON_VALUE;

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
  private final PageModelAssembler pageModelAssembler;
  private final PagedResourcesAssembler<CartoonPage> cartoonPagePagedResourcesAssembler;

  @Operation(summary = "Returns the page with the given ID")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = CartoonPageDTO.class))
  })
  @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  public CartoonPageDTO getPageById(@PathVariable Long id) {
    CartoonPage result = pageService.getPageById(id);
    return pageModelAssembler.toModel(result);
  }

  @Operation(summary = "Returns the pages associated with the given chapter")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonPageDTO.class)))
  })
  @GetMapping(produces = APPLICATION_HAL_JSON_VALUE)
  public PagedModel<CartoonPageDTO> getChapterPages(
      @RequestParam Long chapterId,
      @PageableDefault
      @SortDefault(sort = "pageNumber")
      @ParameterObject
      Pageable pageable
  ) {
    Page<CartoonPage> result = pageService.getChapterPages(chapterId, pageable);
    return cartoonPagePagedResourcesAssembler.toModel(result, pageModelAssembler);
  }

  @Operation(summary = "Creates a new page for the given chapter")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Page created", content = {
          @Content(schema = @Schema(implementation = CartoonPageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @PostMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonPageDTO> createPage(
      @RequestParam Long chapterId,
      @RequestBody @Valid CartoonPageDTO payload
  ) throws URISyntaxException {
    CartoonPage newPage = mapper.toPage(payload);
    CartoonPage result = pageService.createPage(chapterId, newPage);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(pageModelAssembler.toModel(result));
  }

  @Operation(summary = "Partially updates a page with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Page updated", content = {
          @Content(schema = @Schema(implementation = CartoonPageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @PatchMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonPageDTO updatePage(
      @PathVariable Long id,
      @RequestBody @Valid CartoonPageDTO payload
  ) {
    CartoonPage updatedPage = mapper.toPage(payload);
    CartoonPage result = pageService.updatePage(id, updatedPage);
    return pageModelAssembler.toModel(result);
  }

  @Operation(summary = "Deletes a page with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Page deleted", content = {
          @Content(schema = @Schema(implementation = CartoonPageDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonPageDTO deletePage(@PathVariable Long id) {
    CartoonPage result = pageService.deletePage(id);
    return mapper.toDto(result);
  }

}
