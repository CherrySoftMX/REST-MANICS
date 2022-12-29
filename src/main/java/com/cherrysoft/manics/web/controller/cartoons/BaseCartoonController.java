package com.cherrysoft.manics.web.controller.cartoons;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
public abstract class BaseCartoonController {
  protected final CartoonService cartoonService;
  protected final CartoonMapper mapper;
  private final String baseUrl;

  protected final ResponseEntity<List<CartoonResponseDTO>> getCartoons(CartoonType type, Pageable pageable) {
    List<Cartoon> result = cartoonService.getCartoons(type, pageable);
    return ResponseEntity.ok(mapper.toReponseDtoList(result));
  }

  protected final ResponseEntity<CartoonResponseDTO> createCartoon(CartoonDTO cartoonDto) {
    var cartoonSpec = createCartoonSpec(cartoonDto);
    Cartoon result = cartoonService.createCartoon(cartoonSpec);
    try {
      return ResponseEntity
          .created(new URI(String.format("%s/%s", baseUrl, result.getId())))
          .body(mapper.toResponseDto(result));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
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
