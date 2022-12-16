package com.cherrysoft.manics.controller.v2.cartoons;

import com.cherrysoft.manics.controller.v2.dto.cartoons.CategoryDTO;
import com.cherrysoft.manics.controller.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.mappers.v2.CategoryMapperV2;
import com.cherrysoft.manics.model.v2.CategoryV2;
import com.cherrysoft.manics.service.v2.cartoons.CategoryServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("categories")
public class CategoryController {
  private final CategoryServiceV2 categoryService;
  private final CategoryMapperV2 mapper;

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
    CategoryV2 result = categoryService.getCategory(id);
    return ResponseEntity.ok().body(mapper.toDto(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDto) {
    CategoryV2 newCategory = mapper.toCategory(categoryDto);
    CategoryV2 result = categoryService.createCategory(newCategory);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @PathVariable Long id,
      @RequestBody @Valid CategoryDTO categoryDto
  ) {
    CategoryV2 updatedCategory = mapper.toCategory(categoryDto);
    CategoryV2 result = categoryService.updateCategory(id, updatedCategory);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
    CategoryV2 result = categoryService.deleteCategory(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
