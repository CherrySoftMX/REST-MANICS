package com.cherrysoft.manics.web.controller.cartoons;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
import com.cherrysoft.manics.model.specs.ComicCartoonSpec;
import com.cherrysoft.manics.service.CartoonService;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.hateoas.assemblers.CartoonModelAssembler;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;
import static com.cherrysoft.manics.util.MediaTypeUtils.APPLICATION_HAL_JSON_VALUE;

@RestController
@Validated
@RequestMapping(ComicController.BASE_URL)
@Tag(name = "Comics", description = "Manage comics")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class ComicController extends BaseCartoonController {
  public static final String BASE_URL = "/comics";
  private final CartoonModelAssembler cartoonModelAssembler;

  public ComicController(
      CartoonService cartoonService,
      CartoonMapper mapper,
      CartoonModelAssembler cartoonModelAssembler,
      PagedResourcesAssembler<Cartoon> cartoonPagedResourcesAssembler
  ) {
    super(cartoonService, mapper, cartoonModelAssembler, cartoonPagedResourcesAssembler, BASE_URL);
    this.cartoonModelAssembler = cartoonModelAssembler;
  }

  @Operation(summary = "Returns the comic with the given ID")
  @ApiResponse(responseCode = "200", description = "Comic found", content = {
      @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
  })
  @GetMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  public CartoonResponseDTO getComicById(@PathVariable Long id) {
    Cartoon result = cartoonService.getCartoonByIdAndType(id, CartoonType.COMIC);
    return cartoonModelAssembler.toModel(result);
  }

  @Operation(summary = "Returns all the available comics")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping(produces = APPLICATION_HAL_JSON_VALUE)
  public PagedModel<CartoonResponseDTO> getComics(
      @PageableDefault
      @SortDefault(sort = "name")
      @ParameterObject
      Pageable pageable
  ) {
    return super.getCartoons(CartoonType.COMIC, pageable);
  }

  @Operation(summary = "Creates a new comic")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Comic created", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping(produces = APPLICATION_HAL_JSON_VALUE)
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> createComic(@RequestBody @Valid CartoonDTO payload) {
    return super.createCartoon(payload);
  }

  @Operation(summary = "Partially updates a comic with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Comic updated", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping(value = "/{id}", produces = APPLICATION_HAL_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonResponseDTO updateComic(
      @PathVariable Long id,
      @RequestBody @Valid CartoonDTO payload
  ) {
    return super.updateCartoon(id, payload);
  }

  @Operation(summary = "Deletes a comic with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Comic deleted", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonResponseDTO deleteComic(@PathVariable Long id) {
    return super.deleteCartoon(id, CartoonType.COMIC);
  }

  @Override
  protected CartoonSpec createCartoonSpec(CartoonDTO payload) {
    Cartoon providedCartoon = mapper.toCartoon(payload);
    return new ComicCartoonSpec(providedCartoon, payload.getCategoryIds());
  }

}
