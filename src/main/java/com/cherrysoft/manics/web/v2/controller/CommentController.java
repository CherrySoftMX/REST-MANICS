package com.cherrysoft.manics.web.v2.controller;

import com.cherrysoft.manics.model.v2.CommentV2;
import com.cherrysoft.manics.model.v2.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.v2.specs.CreateCommentSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.v2.CommentServiceV2;
import com.cherrysoft.manics.web.v2.dto.CommentDTO;
import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.web.v2.mapper.v2.CommentMapperV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(CommentController.BASE_URL)
public class CommentController {
  public static final String BASE_URL = "/comments";
  private final CommentServiceV2 commentService;
  private final CommentMapperV2 mapper;

  @GetMapping
  public ResponseEntity<List<CommentDTO>> getComments(
      @RequestParam Map<String, String> params
  ) {
    var filterSpec = new CommentFilterSpec(params);
    List<CommentV2> result = commentService.getComments(filterSpec);
    return ResponseEntity.ok(mapper.toDtoList(result));
  }

  @PostMapping
  @Validated(OnCreate.class)
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<CommentDTO> createComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @RequestParam Long cartoonId,
      @RequestParam Long userId,
      @RequestBody @Valid CommentDTO commentDto
  ) {
    CommentV2 comment = mapper.toComment(commentDto);
    var createCommentSpec = new CreateCommentSpec(cartoonId, userId, comment);
    CommentV2 result = commentService.createComment(createCommentSpec);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("#loggedUser.id == #userId")
  public ResponseEntity<CommentDTO> updateComment(
      @AuthenticationPrincipal SecurityManicUser loggedUser,
      @PathVariable Long id,
      @RequestParam Long userId,
      @RequestBody @Valid CommentDTO commentDto
  ) {
    CommentV2 updatedComment = mapper.toComment(commentDto);
    CommentV2 result = commentService.updateComment(id, updatedComment);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long id) {
    CommentV2 result = commentService.deleteComment(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
