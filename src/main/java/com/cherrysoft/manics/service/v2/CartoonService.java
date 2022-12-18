package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.CartoonNotFoundException;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import com.cherrysoft.manics.model.v2.specs.CartoonSpec;
import com.cherrysoft.manics.repository.v2.CartoonRepository;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartoonService {
  private final CartoonRepository cartoonRepository;
  private final CategoryServiceV2 categoryService;

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

  public List<Cartoon> getCartoons(CartoonType cartoonType) {
    return cartoonRepository.findCartoonsByType(cartoonType);
  }

  public Cartoon createCartoon(CartoonSpec spec) {
    Cartoon newCartoon = spec.getCartoon();
    Set<Long> categoryIds = spec.getCategoryIds();
    var categoryReferences = categoryService.getCategoriesById(categoryIds);
    newCartoon.setCategories(categoryReferences);
    return cartoonRepository.save(newCartoon);
  }

  // Only basic fields can be updated with this method
  public Cartoon updateCartoon(Long id, CartoonSpec spec) {
    Cartoon updatedCartoon = spec.getCartoon();
    Cartoon cartoon = getCartoonByIdAndType(id, updatedCartoon.getType());
    updatedCartoon.setChapters(null);
    updatedCartoon.setCategories(null);
    updatedCartoon.setComments(null);
    BeanUtils.copyProperties(updatedCartoon, cartoon);
    return cartoonRepository.save(cartoon);
  }

  public Cartoon deleteCartoon(Long id, CartoonType type) {
    Cartoon cartoon = getCartoonByIdAndType(id, type);
    cartoon.removeLikes();
    cartoonRepository.deleteById(id);
    return cartoon;
  }

}
