package com.cherrysoft.manics.web.controller.cartoons;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.model.specs.MangaCartoonSpec;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RestController
@RequestMapping(MangaController.BASE_URL)
@Tag(name = "Mangas", description = "Manage mangas")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class MangaController extends BaseCartoonController {
  public static final String BASE_URL = "/mangas";

  public MangaController(CartoonService cartoonService, CartoonMapper mapper) {
    super(cartoonService, mapper, BASE_URL);
  }

  @Operation(summary = "Returns all the available mangas")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<CartoonResponseDTO>> getMangas(
      @PageableDefault
      @SortDefault(sort = "name")
      @ParameterObject
      Pageable pageable
  ) {
    return super.getCartoons(CartoonType.MANGA, pageable);
  }

  @Operation(summary = "Creates a new manga")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Manga created", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> createManga(@RequestBody @Valid CartoonDTO payload) {
    return super.createCartoon(payload);
  }

  @Operation(summary = "Partially updates a manga with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Comic manga", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> updateManga(
      @PathVariable Long id,
      @RequestBody @Valid CartoonDTO payload
  ) {
    return super.updateCartoon(id, payload);
  }

  @Operation(summary = "Deletes a manga with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Manga deleted", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> deleteManga(@PathVariable Long id) {
    return super.deleteCartoon(id, CartoonType.MANGA);
  }

  @Override
  protected CartoonSpec createCartoonSpec(CartoonDTO payload) {
    Cartoon providedCartoon = mapper.toCartoon(payload);
    return new MangaCartoonSpec(providedCartoon, payload.getCategoryIds());
  }

}
