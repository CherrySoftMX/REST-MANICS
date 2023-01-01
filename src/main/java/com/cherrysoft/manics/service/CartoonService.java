package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.CartoonNotFoundException;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.repository.CartoonRepository;
import com.cherrysoft.manics.service.search.SearchingPageService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartoonService {
  private final CategoryService categoryService;
  private final CartoonRepository cartoonRepository;
  private final SearchingPageService searchingPageService;

  public Page<Cartoon> searchCartoon(String query, Pageable pageable) {
    return cartoonRepository.searchCartoons(query, pageable);
  }

  public Page<Cartoon> getCartoons(CartoonType cartoonType, Pageable pageable) {
    return cartoonRepository.findCartoonsByType(cartoonType, pageable);
  }

  public Cartoon getCartoonById(Long cartoonId) {
    return cartoonRepository
        .findById(cartoonId)
        .orElseThrow(() -> new CartoonNotFoundException(cartoonId));
  }

  Cartoon getCartonReferenceById(Long id) {
    return cartoonRepository.getReferenceById(id);
  }

  public Cartoon getCartoonByIdAndType(Long id, CartoonType type) {
    return cartoonRepository
        .findCartoonByIdAndType(id, type)
        .orElseThrow(() -> new CartoonNotFoundException(id, type));
  }

  @Transactional
  public Cartoon createCartoon(CartoonSpec spec) {
    Cartoon newCartoon = spec.getCartoon();
    Set<Long> categoryIds = spec.getCategoryIds();
    var categoryRefs = categoryService.getCategoriesById(categoryIds);
    newCartoon.setCategories(categoryRefs);
    Cartoon result = cartoonRepository.save(newCartoon);
    searchingPageService.indexPagesFromChapters(result.getChapters());
    return result;
  }

  // Only basic fields can be updated with this method
  public Cartoon updateCartoon(Long id, CartoonSpec spec) {
    Cartoon updatedCartoon = spec.getCartoon();
    Cartoon cartoon = getCartoonByIdAndType(id, updatedCartoon.getType());
    updatedCartoon.setChapters(null);
    BeanUtils.copyProperties(updatedCartoon, cartoon);
    Set<Category> categories = categoryService.getCategoriesById(spec.getCategoryIds());
    cartoon.updateCategories(categories);
    return cartoonRepository.save(cartoon);
  }

  @Transactional
  public Cartoon deleteCartoon(Long id, CartoonType type) {
    Cartoon cartoon = getCartoonByIdAndType(id, type);
    cartoon.removeLikes();
    cartoon.removeBookmarks();
    cartoonRepository.deleteById(id);
    searchingPageService.deleteIndexedPagesFromChapters(cartoon.getChapters());
    return cartoon;
  }

}
