package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.service.CategoryService;
import com.cherrysoft.manics.web.dto.CategoryDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(CategoryController.BASE_URL)
@Tag(name = "Categories", description = "Manage categories for cartoons")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class CategoryController {
  public static final String BASE_URL = "/categories";
  private final CategoryService categoryService;
  private final CategoryMapper mapper;

  @Operation(summary = "Returns the category with the given ID")
  @ApiResponse(responseCode = "200", description = "Category found", content = {
      @Content(schema = @Schema(implementation = CategoryDTO.class))
  })
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
    Category result = categoryService.getCategory(id);
    return ResponseEntity.ok().body(mapper.toDto(result));
  }

  @Operation(summary = "Creates a new category for the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Category created", content = {
          @Content(schema = @Schema(implementation = CategoryDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO payload) throws URISyntaxException {
    Category newCategory = mapper.toCategory(payload);
    Category result = categoryService.createCategory(newCategory);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toDto(result));
  }

  @Operation(summary = "Partially updates a category with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Category updated", content = {
          @Content(schema = @Schema(implementation = CategoryDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable Long id,
      @RequestBody @Valid CategoryDTO payload
  ) {
    Category updatedCategory = mapper.toCategory(payload);
    Category result = categoryService.updateCategory(id, updatedCategory);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @Operation(summary = "Deletes a category with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Category deleted", content = {
          @Content(schema = @Schema(implementation = CategoryDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
    Category result = categoryService.deleteCategory(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
