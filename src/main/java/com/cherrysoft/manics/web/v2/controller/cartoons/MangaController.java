package com.cherrysoft.manics.web.v2.controller.cartoons;

import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CartoonType;
import com.cherrysoft.manics.model.v2.specs.CartoonSpec;
import com.cherrysoft.manics.model.v2.specs.MangaCartoonSpec;
import com.cherrysoft.manics.service.v2.CartoonService;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.web.v2.mapper.v2.CartoonMapper;
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
@RequestMapping(MangaController.BASE_URL)
public class MangaController extends BaseCartoonController {
  public static final String BASE_URL = "/mangas_v2";

  public MangaController(CartoonService cartoonService, CartoonMapper mapper) {
    super(cartoonService, mapper);
  }

  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> getMangas(
      @PageableDefault @SortDefault(sort = "name") Pageable pageable
  ) {
    return super.getCartoons(CartoonType.MANGA, pageable);
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> createManga(@RequestBody @Valid CartoonDTO cartoonDto) {
    return super.createCartoon(cartoonDto);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> updateManga(
      @PathVariable Long id,
      @RequestBody @Valid CartoonDTO cartoonDto
  ) {
    return super.updateCartoon(id, cartoonDto);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> deleteManga(@PathVariable Long id) {
    return super.deleteCartoon(id, CartoonType.MANGA);
  }

  @Override
  protected CartoonSpec createCartoonSpec(CartoonDTO cartoonDto) {
    Cartoon providedCartoon = mapper.toCartoon(cartoonDto);
    return new MangaCartoonSpec(providedCartoon, cartoonDto.getCategoryIds());
  }

}
