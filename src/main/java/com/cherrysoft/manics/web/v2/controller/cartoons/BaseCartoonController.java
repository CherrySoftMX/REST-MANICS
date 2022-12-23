package com.cherrysoft.manics.web.v2.controller.cartoons;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import com.cherrysoft.manics.model.v2.specs.CartoonSpec;
import com.cherrysoft.manics.service.v2.CartoonService;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.v2.mapper.v2.CartoonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseCartoonController {
  protected final CartoonService cartoonService;
  protected final CartoonMapper mapper;

  protected final ResponseEntity<List<CartoonResponseDTO>> getCartoons(CartoonType type, Pageable pageable) {
    List<Cartoon> result = cartoonService.getCartoons(type, pageable);
    return ResponseEntity.ok(mapper.toReponseDtoList(result));
  }

  protected final ResponseEntity<CartoonResponseDTO> createCartoon(CartoonDTO cartoonDto) {
    var cartoonSpec = createCartoonSpec(cartoonDto);
    Cartoon result = cartoonService.createCartoon(cartoonSpec);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  protected final ResponseEntity<CartoonResponseDTO> updateCartoon(Long id, CartoonDTO cartoonDto) {
    var cartoonSpec = createCartoonSpec(cartoonDto);
    Cartoon result = cartoonService.updateCartoon(id, cartoonSpec);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  protected final ResponseEntity<CartoonResponseDTO> deleteCartoon(Long id, CartoonType type) {
    Cartoon result = cartoonService.deleteCartoon(id, type);
    return ResponseEntity.ok(mapper.toResponseDto(result));
  }

  protected abstract CartoonSpec createCartoonSpec(CartoonDTO cartoonDto);

}
