package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.CartoonType;
import com.cherrysoft.manics.model.specs.CartoonSpec;
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
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
import java.net.URI;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RestController
@RequestMapping(CartoonController.BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Cartoons", description = "Manage cartoons")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(ref = INTERNAL_SERVER_ERROR_RESPONSE_REF, responseCode = "500")
})
public class CartoonController {
  public static final String BASE_URL = "/cartoons";
  private final CartoonService cartoonService;
  private final CartoonMapper mapper;
  private final CartoonModelAssembler cartoonModelAssembler;
  private final PagedResourcesAssembler<Cartoon> cartoonPagedResourcesAssembler;

  @Operation(summary = "Returns the cartoon with the given ID")
  @ApiResponse(responseCode = "200", description = "Cartoon found", content = {
      @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
  })
  @GetMapping("/{id}")
  public CartoonResponseDTO getCartoonById(@PathVariable Long id) {
    Cartoon result = cartoonService.getCartoonByIdAndType(id, CartoonType.COMIC);
    return cartoonModelAssembler.toModel(result);
  }

  @Operation(summary = "Returns all the cartoons with the given type")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping
  public PagedModel<CartoonResponseDTO> getCartoons(
      @RequestParam String type,
      @PageableDefault
      @SortDefault(sort = "name")
      @ParameterObject
      Pageable pageable
  ) {
    Page<Cartoon> result = cartoonService.getCartoons(CartoonType.of(type), pageable);
    return cartoonPagedResourcesAssembler.toModel(result, cartoonModelAssembler);
  }

  @Operation(summary = "Creates a new cartoon")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Cartoon created", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CartoonResponseDTO> createCartoon(
      @RequestParam String type,
      @RequestBody @Valid CartoonDTO payload
  ) {
    Cartoon providedCartoon = mapper.toCartoon(payload);
    var spec = new CartoonSpec(providedCartoon, CartoonType.of(type), payload.getCategoryIds());
    Cartoon result = cartoonService.createCartoon(spec);
    return ResponseEntity
        .created(URI.create(String.format("%s/%s", BASE_URL, result.getId())))
        .body(cartoonModelAssembler.toModel(result));
  }

  @Operation(summary = "Partially updates a cartoon with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Cartoon updated", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonResponseDTO updateCartoon(
      @PathVariable Long id,
      @RequestParam String type,
      @RequestBody @Valid CartoonDTO payload
  ) {
    Cartoon providedCartoon = mapper.toCartoon(payload);
    var spec = new CartoonSpec(providedCartoon, CartoonType.of(type), payload.getCategoryIds());
    Cartoon result = cartoonService.updateCartoon(id, spec);
    return cartoonModelAssembler.toModel(result);
  }

  @Operation(summary = "Deletes a cartoon with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Cartoon deleted", content = {
          @Content(schema = @Schema(implementation = CartoonResponseDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public CartoonResponseDTO deleteCartoon(
      @PathVariable Long id,
      @RequestParam String type
  ) {
    Cartoon result = cartoonService.deleteCartoon(id, CartoonType.of(type));
    return mapper.toResponseDto(result);
  }

}
