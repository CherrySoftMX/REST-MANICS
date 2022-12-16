package com.cherrysoft.manics.service.legacy.stories;

import com.cherrysoft.manics.exception.legacy.CategoryInUseException;
import com.cherrysoft.manics.exception.legacy.NotFoundException;
import com.cherrysoft.manics.model.legacy.core.Category;
import com.cherrysoft.manics.repository.legacy.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Autowired
  private StoryService storyService;

  private CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getCategories() {
    List<Category> categories = new ArrayList<>();
    categoryRepository.findAll().iterator().forEachRemaining(categories::add);
    return categories;
  }

  public Category getCategory(Integer categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() ->
            new NotFoundException(String.format("No encontramos la categoria con el id: %d", categoryId))
        );
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Category updateCategory(Integer categoryId, Category newCategory) {
    Category category = getCategory(categoryId);
    category.setDescription(newCategory.getDescription());
    category.setName(newCategory.getName());
    return categoryRepository.save(category);
  }

  public Category deleteCategory(Integer categoryId) {
    Category category = getCategory(categoryId);
    if (storyService.isCategoryBeingUse(category)) {
      throw new CategoryInUseException();
    }
    categoryRepository.delete(category);
    return category;
  }

}
