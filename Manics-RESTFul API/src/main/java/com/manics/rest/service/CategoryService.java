package com.manics.rest.service;

import com.manics.rest.model.core.Category;
import com.manics.rest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MangaService mangaService;

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(categories::add);
        return categories;
    }

    public Category getCategory(Integer categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("No found"));
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

        if (mangaService.isCategoryBeingUse(category))
            throw new RuntimeException("La categoria no puede eliminarse");

        categoryRepository.delete(category);

        return category;
    }


}
