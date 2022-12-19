package com.cherrysoft.manics.web.v2.controller.cartoons;

import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.web.v2.mapper.v2.CartoonMapper;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import com.cherrysoft.manics.model.v2.specs.CartoonSpec;
import com.cherrysoft.manics.model.v2.specs.ComicCartoonSpec;
import com.cherrysoft.manics.service.v2.CartoonService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping(ComicController.BASE_URL)
public class ComicController extends CartoonController {
  public static final String BASE_URL = "/comics_v2";

  public ComicController(CartoonService cartoonService, CartoonMapper mapper) {
    super(cartoonService, mapper);
  }

  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> getComics() {
    return super.getCartoons(CartoonType.COMIC);
  }

  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<CartoonResponseDTO> createComic(@RequestBody @Valid CartoonDTO cartoonDto) {
    return super.createCartoon(cartoonDto);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CartoonResponseDTO> updateComic(
      @PathVariable Long id,
      @RequestBody @Valid CartoonDTO cartoonDto
  ) {
    return super.updateCartoon(id, cartoonDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CartoonResponseDTO> deleteComic(@PathVariable Long id) {
    return super.deleteCartoon(id, CartoonType.COMIC);
  }

  @Override
  protected CartoonSpec createCartoonSpec(CartoonDTO cartoonDto) {
    Cartoon providedCartoon = mapper.toCartoon(cartoonDto);
    return new ComicCartoonSpec(providedCartoon, cartoonDto.getCategoryIds());
  }

}
