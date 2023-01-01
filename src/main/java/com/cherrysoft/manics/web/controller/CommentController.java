package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.model.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.specs.CreateCommentSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.CommentService;
import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.hateoas.assemblers.CommentModelAssembler;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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
  private final CommentModelAssembler commentModelAssembler;
  private final PagedResourcesAssembler<Comment> commentPagedResourcesAssembler;

  @Operation(summary = "Returns the comments with the content that match with the given content")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))
  })
  @Parameter(
      name = "content",
      description = "The content to search for",
      schema = @Schema(type = "string")
  )
  @GetMapping("/search")
  public PagedModel<CommentDTO> searchCommentsByContent(
      @RequestParam String content,
      @PageableDefault Pageable pageable
  ) {
    Page<Comment> result = commentService.searchCommentsByContent(content, pageable);
    return commentPagedResourcesAssembler.toModel(result, commentModelAssembler);
  }

  @Operation(summary = "Returns the comments for the given user, cartoon OR comment")
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
  @Parameter(
      name = "commentId",
      description = "The ID of the parent comment",
      schema = @Schema(type = "number")
  )
  @GetMapping
  public PagedModel<CommentDTO> getComments(
      @Parameter(hidden = true) @RequestParam Map<String, String> params,
      @PageableDefault
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @ParameterObject
      Pageable pageable
  ) {
    var filterSpec = new CommentFilterSpec(params, pageable);
    Page<Comment> result = commentService.getComments(filterSpec);
    return commentPagedResourcesAssembler.toModel(result, commentModelAssembler).withFallbackType(CommentDTO.class);
  }

  @Operation(summary = "Returns the comment with the given ID")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = CommentDTO.class))
  })
  @GetMapping("/{id}")
  public CommentDTO getCommentById(@PathVariable Long id) {
    Comment result = commentService.getCommentById(id);
    return commentModelAssembler.toModel(result);
  }

  @Operation(summary = "Creates a new comment for the given user and cartoon")
  @ApiResponse(responseCode = "201", description = "Comment created", content = {
      @Content(schema = @Schema(implementation = CommentDTO.class))
  })
  @PostMapping
  @Validated(OnCreate.class)
  public ResponseEntity<CommentDTO> createComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long parentId,
      @RequestBody @Valid CommentDTO payload
  ) {
    Comment comment = mapper.toComment(payload);
    var createCommentSpec = new CreateCommentSpec(cartoonId, loggedUser.getId(), parentId, comment);
    Comment result = commentService.createComment(createCommentSpec);
    return ResponseEntity
        .created(URI.create(String.format("%s/%s", BASE_URL, result.getId())))
        .body(commentModelAssembler.toModel(result));
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
  public CommentDTO updateComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid CommentDTO payload
  ) {
    Comment updatedComment = mapper.toComment(payload);
    Comment result = commentService.updateComment(id, updatedComment);
    return commentModelAssembler.toModel(result);
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
