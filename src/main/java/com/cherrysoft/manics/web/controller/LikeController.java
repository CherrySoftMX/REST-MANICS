package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.LikedResult;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.LikeSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.LikeService;
import com.cherrysoft.manics.web.dto.LikedResultDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.hateoas.assemblers.CartoonModelAssembler;
import com.cherrysoft.manics.web.hateoas.assemblers.ManicUserModelAssembler;
import com.cherrysoft.manics.web.mapper.LikedResultMapper;
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
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(LikeController.BASE_URL)
@Tag(name = "Likes", description = "Manage likes for a cartoon")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(ref = INTERNAL_SERVER_ERROR_RESPONSE_REF, responseCode = "500")
})
public class LikeController {
  public static final String BASE_URL = "/likes";
  private final LikeService likeService;
  private final LikedResultMapper likeMapper;
  private final ManicUserModelAssembler userModelAssembler;
  private final PagedResourcesAssembler<ManicUser> userPagedResourcesAssembler;
  private final CartoonModelAssembler cartoonModelAssembler;
  private final PagedResourcesAssembler<Cartoon> cartoonPagedResourcesAssembler;

  @Operation(summary = "Returns the users who like the given cartoon")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = ManicUserDTO.class)))
  })
  @GetMapping("/cartoon")
  public PagedModel<ManicUserDTO> getLikedBy(
      @RequestParam Long cartoonId,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    Page<ManicUser> result = likeService.getLikedBy(cartoonId, pageable);
    return userPagedResourcesAssembler.toModel(result, userModelAssembler);
  }

  @Operation(summary = "Returns the liked cartoons by the given user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping("/user")
  public PagedModel<CartoonResponseDTO> getLikes(
      @RequestParam Long userId,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    Page<Cartoon> result = likeService.getLikes(userId, pageable);
    return cartoonPagedResourcesAssembler.toModel(result, cartoonModelAssembler);
  }

  @Operation(summary = "Like the given cartoon")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(schema = @Schema(implementation = LikedResultDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<LikedResultDTO> like(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var likeSpec = new LikeSpec(cartoonId, userId);
    LikedResult result = likeService.like(likeSpec);
    return ResponseEntity.ok(likeMapper.toDto(result));
  }

}
