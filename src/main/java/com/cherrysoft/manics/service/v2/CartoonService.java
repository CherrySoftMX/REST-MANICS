package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.CartoonNotFoundException;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import com.cherrysoft.manics.model.v2.search.SearchCartoonResult;
import com.cherrysoft.manics.model.v2.specs.CartoonSpec;
import com.cherrysoft.manics.repository.v2.CartoonRepository;
import com.cherrysoft.manics.service.v2.search.SearchingCartoonService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartoonService {
  private final CategoryServiceV2 categoryService;
  private final SearchingCartoonService searchingCartoonService;
  private final CartoonRepository cartoonRepository;

  public List<Cartoon> searchCartoonByName(String name, Pageable pageable) {
    SearchCartoonResult searchResult = searchingCartoonService.searchByCartoonName(name, pageable);
    return cartoonRepository.findAllById(searchResult.getMatchingCartoonIds());
  }

  public List<Cartoon> getCartoons(CartoonType cartoonType, Pageable pageable) {
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
    var categoryReferences = categoryService.getCategoriesById(categoryIds);
    newCartoon.setCategories(categoryReferences);
    Cartoon result = cartoonRepository.save(newCartoon);
    searchingCartoonService.indexCartoonForSearching(result);
    return result;
  }

  // Only basic fields can be updated with this method
  @Transactional
  public Cartoon updateCartoon(Long id, CartoonSpec spec) {
    Cartoon updatedCartoon = spec.getCartoon();
    Cartoon cartoon = getCartoonByIdAndType(id, updatedCartoon.getType());
    updatedCartoon.setChapters(null);
    updatedCartoon.setCategories(null);
    updatedCartoon.setComments(null);
    BeanUtils.copyProperties(updatedCartoon, cartoon);
    Cartoon result = cartoonRepository.save(cartoon);
    searchingCartoonService.updateIndexedCartoon(id, result);
    return result;
  }

  @Transactional
  public Cartoon deleteCartoon(Long id, CartoonType type) {
    Cartoon cartoon = getCartoonByIdAndType(id, type);
    cartoon.removeLikes();
    cartoon.removeBookmarks();
    searchingCartoonService.deleteIndexedCartoon(cartoon);
    cartoonRepository.deleteById(id);
    return cartoon;
  }

}
