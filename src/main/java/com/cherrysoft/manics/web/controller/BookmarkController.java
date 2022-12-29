package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.BookmarkedResult;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.specs.BookmarkSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.BookmarkService;
import com.cherrysoft.manics.web.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.mapper.BookmarkedResultMapper;
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
@RequestMapping(BookmarkController.BASE_URL)
@Tag(name = "Bookmarks", description = "Manage bookmarks for a cartoon")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class BookmarkController {
  public static final String BASE_URL = "/bookmarks";
  private final BookmarkService bookmarkService;
  private final BookmarkedResultMapper bookmarkMapper;
  private final CartoonMapper cartoonMapper;

  @Operation(summary = "Returns the bookmarked cartoons liked by the given user")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CartoonResponseDTO.class)))
  })
  @GetMapping("/user")
  public ResponseEntity<List<CartoonResponseDTO>> getBookmarks(
      @RequestParam Long userId,
      @PageableDefault @ParameterObject Pageable pageable
  ) {
    List<Cartoon> result = bookmarkService.getBookmarks(userId, pageable);
    return ResponseEntity.ok(cartoonMapper.toReponseDtoList(result));
  }

  @Operation(summary = "Bookmark the given cartoon")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK", content = {
          @Content(schema = @Schema(implementation = BookmarkedResultDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<BookmarkedResultDTO> bookmark(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId
  ) {
    var bookmarkSpec = new BookmarkSpec(cartoonId, userId);
    BookmarkedResult result = bookmarkService.bookmark(bookmarkSpec);
    return ResponseEntity.ok(bookmarkMapper.toDto(result));
  }

}
