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
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import com.cherrysoft.manics.web.mapper.LikedResultMapper;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(LikeController.BASE_URL)
@Tag(name = "Likes", description = "Manage likes for a cartoon")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class LikeController {
  public static final String BASE_URL = "/likes";
  private final LikeService likeService;
  private final LikedResultMapper likeMapper;
  private final CartoonMapper cartoonMapper;
  private final ManicUserMapper userMapper;

  @Operation(summary = "Returns the users who like the given cartoon")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = ManicUserDTO.class)))
  })
  @GetMapping("/cartoon")
  public ResponseEntity<List<ManicUserDTO>> getLikedBy(
      @RequestParam Long cartoonId,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    List<ManicUser> result = likeService.getLikedBy(cartoonId, pageable);
    return ResponseEntity.ok(userMapper.toDtoList(result));
  }

  @Operation(summary = "Returns the liked cartoons by the given user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping("/user")
  public ResponseEntity<List<CartoonResponseDTO>> getLikes(
      @RequestParam Long userId,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    List<Cartoon> result = likeService.getLikes(userId, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
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
