package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.service.CategoryService;
import com.cherrysoft.manics.web.dto.CategoryDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("categories")
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
    Category result = categoryService.getCategory(id);
    return ResponseEntity.ok().body(mapper.toDto(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDto) {
    Category newCategory = mapper.toCategory(categoryDto);
    Category result = categoryService.createCategory(newCategory);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable Long id,
      @RequestBody @Valid CategoryDTO categoryDto
  ) {
    Category updatedCategory = mapper.toCategory(categoryDto);
    Category result = categoryService.updateCategory(id, updatedCategory);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
    Category result = categoryService.deleteCategory(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
