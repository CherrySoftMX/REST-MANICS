package com.cherrysoft.manics.web.controller.cartoons;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.model.specs.ComicCartoonSpec;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping(ComicController.BASE_URL)
public class ComicController extends BaseCartoonController {
  public static final String BASE_URL = "/comics";

  public ComicController(CartoonService cartoonService, CartoonMapper mapper) {
    super(cartoonService, mapper);
  }

  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> getComics(
      @PageableDefault @SortDefault(sort = "name") Pageable pageable
  ) {
    return super.getCartoons(CartoonType.COMIC, pageable);
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> createComic(@RequestBody @Valid CartoonDTO cartoonDto) {
    return super.createCartoon(cartoonDto);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> updateComic(
      @PathVariable Long id,
      @RequestBody @Valid CartoonDTO cartoonDto
  ) {
    return super.updateCartoon(id, cartoonDto);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> deleteComic(@PathVariable Long id) {
    return super.deleteCartoon(id, CartoonType.COMIC);
  }

  @Override
  protected CartoonSpec createCartoonSpec(CartoonDTO cartoonDto) {
    Cartoon providedCartoon = mapper.toCartoon(cartoonDto);
    return new ComicCartoonSpec(providedCartoon, cartoonDto.getCategoryIds());
  }

}
