package com.cherrysoft.manics.web.controller.cartoons;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.hateoas.assemblers.CartoonModelAssembler;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
public abstract class BaseCartoonController {
  protected final CartoonService cartoonService;
  protected final CartoonMapper mapper;
  private final CartoonModelAssembler baseCartoonModelAssembler;
  private final PagedResourcesAssembler<Cartoon> pagedResourcesAssembler;
  private final String baseUrl;

  protected final PagedModel<CartoonResponseDTO> getCartoons(CartoonType type, Pageable pageable) {
    Page<Cartoon> result = cartoonService.getCartoons(type, pageable);
    return pagedResourcesAssembler.toModel(result, baseCartoonModelAssembler);
  }

  protected final ResponseEntity<CartoonResponseDTO> createCartoon(CartoonDTO cartoonDto) {
    var cartoonSpec = createCartoonSpec(cartoonDto);
    Cartoon result = cartoonService.createCartoon(cartoonSpec);
    try {
      return ResponseEntity
          .created(new URI(String.format("%s/%s", baseUrl, result.getId())))
          .body(baseCartoonModelAssembler.toModel(result));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  protected final CartoonResponseDTO updateCartoon(Long id, CartoonDTO cartoonDto) {
    var cartoonSpec = createCartoonSpec(cartoonDto);
    Cartoon result = cartoonService.updateCartoon(id, cartoonSpec);
    return baseCartoonModelAssembler.toModel(result);
  }

  protected final CartoonResponseDTO deleteCartoon(Long id, CartoonType type) {
    Cartoon result = cartoonService.deleteCartoon(id, type);
    return mapper.toResponseDto(result);
  }

  protected abstract CartoonSpec createCartoonSpec(CartoonDTO cartoonDto);

}
