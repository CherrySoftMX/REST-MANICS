package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.CategoryNotFoundException;
import com.cherrysoft.manics.model.v2.CategoryV2;
import com.cherrysoft.manics.repository.v2.CategoryRepositoryV2;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceV2 {
  private final CategoryRepositoryV2 categoryRepository;

  public CategoryV2 getCategory(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new CategoryNotFoundException(id));
  }

  Set<CategoryV2> getCategoriesById(Set<Long> ids) {
    return new HashSet<>(categoryRepository.findAllById(ids));
  }

  public CategoryV2 createCategory(CategoryV2 category) {
    return categoryRepository.save(category);
  }

  public CategoryV2 updateCategory(Long id, CategoryV2 newCategory) {
    CategoryV2 category = getCategory(id);
    BeanUtils.copyProperties(newCategory, category);
    return categoryRepository.save(category);
  }

  public CategoryV2 deleteCategory(Long id) {
    CategoryV2 category = getCategory(id);
    categoryRepository.delete(category);
    return category;
  }

}