package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.model.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.specs.CreateCommentSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.CommentService;
import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CommentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static com.cherrysoft.manics.util.ApiDocsConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(CommentController.BASE_URL)
@Tag(name = "Comments", description = "Manage comments for a cartoon")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
})
public class CommentController {
  public static final String BASE_URL = "/comments";
  private final CommentService commentService;
  private final CommentMapper mapper;

  @Operation(summary = "Returns the comments for the given user OR cartoon")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))
  })
  @Parameter(
      name = "userId",
      description = "The user ID to whom the comments belong to",
      schema = @Schema(type = "number")
  )
  @Parameter(
      name = "cartoonId",
      description = "The ID of the cartoon that the comments belong to",
      schema = @Schema(type = "number")
  )
  @GetMapping
  public ResponseEntity<List<CommentDTO>> getComments(
      @Parameter(hidden = true) @RequestParam Map<String, String> params,
      @PageableDefault
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @ParameterObject
      Pageable pageable
  ) {
    var filterSpec = new CommentFilterSpec(params, pageable);
    List<Comment> result = commentService.getComments(filterSpec);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @Operation(summary = "Creates a new comment for the given user and cartoon")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Comment created", content = {
          @Content(schema = @Schema(implementation = CommentDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<CommentDTO> createComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId,
      @RequestBody @Valid CommentDTO payload
  ) throws URISyntaxException {
    Comment comment = mapper.toComment(payload);
    var createCommentSpec = new CreateCommentSpec(cartoonId, userId, comment);
    Comment result = commentService.createComment(createCommentSpec);
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, result.getId())))
        .body(mapper.toDto(result));
  }

  @Operation(summary = "Partially updates a comment with the given payload")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Comment updated", content = {
          @Content(schema = @Schema(implementation = CommentDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @PatchMapping("/{id}")
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<CommentDTO> updateComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid CommentDTO payload
  ) {
    Comment updatedComment = mapper.toComment(payload);
    Comment result = commentService.updateComment(id, updatedComment);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @Operation(summary = "Deletes a comment with the given ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Comment deleted", content = {
          @Content(schema = @Schema(implementation = CommentDTO.class))
      }),
      @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long id) {
    Comment result = commentService.deleteComment(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
