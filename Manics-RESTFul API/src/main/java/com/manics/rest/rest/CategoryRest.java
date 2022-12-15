package com.manics.rest.rest;

import com.manics.rest.mappers.CategoryMapper;
import com.manics.rest.model.core.Category;
import com.manics.rest.rest.request.CategoryRequest;
import com.manics.rest.service.stories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("categorias")
@AllArgsConstructor
public class CategoryRest {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok().body(categoryService.getCategories());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
    return ResponseEntity.ok().body(categoryService.getCategory(id));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequest request) {
    return ResponseEntity.ok().body(
        categoryService.createCategory(categoryMapper.categoryRequestToCategory(request))
    );
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Category> updateCategory(
      @PathVariable Integer id,
      @RequestBody @Valid CategoryRequest request
  ) {
    return ResponseEntity.ok().body(
        categoryService.updateCategory(id, categoryMapper.categoryRequestToCategory(request))
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Category> deleteCategory(@PathVariable Integer id) {
    return ResponseEntity.ok().body(categoryService.deleteCategory(id));
  }

}
