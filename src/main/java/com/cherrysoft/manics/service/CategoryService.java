package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.CategoryNotFoundException;
import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.repository.CategoryRepository;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public Page<Category> searchCategories(String query, Pageable pageable) {
    return categoryRepository.searchCategory(query, pageable);
  }

  public Category getCategory(Long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new CategoryNotFoundException(id));
  }

  public Page<Category> getCategoriesOfCartoon(Long cartoonId, Pageable pageable) {
    return categoryRepository.findAllOfCartoon(cartoonId, pageable);
  }

  Set<Category> getCategoriesById(Set<Long> ids) {
    return new HashSet<>(categoryRepository.findAllById(ids));
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Category updateCategory(Long id, Category newCategory) {
    Category category = getCategory(id);
    BeanUtils.copyProperties(newCategory, category);
    return categoryRepository.save(category);
  }

  public Category deleteCategory(Long id) {
    Category category = getCategory(id);
    categoryRepository.delete(category);
    return category;
  }

}
