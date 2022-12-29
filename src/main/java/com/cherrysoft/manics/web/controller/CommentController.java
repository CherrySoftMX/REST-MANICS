package com.cherrysoft.manics.web.controller;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.model.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.specs.CreateCommentSpec;
import com.cherrysoft.manics.security.SecurityManicUser;
import com.cherrysoft.manics.service.CommentService;
import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.web.dto.validation.OnCreate;
import com.cherrysoft.manics.web.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(CommentController.BASE_URL)
public class CommentController {
  public static final String BASE_URL = "/comments";
  private final CommentService commentService;
  private final CommentMapper mapper;

  @GetMapping
  public ResponseEntity<List<CommentDTO>> getComments(
      @RequestParam Map<String, String> params,
      @PageableDefault @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    System.out.println(pageable);
    var filterSpec = new CommentFilterSpec(params, pageable);
    List<Comment> result = commentService.getComments(filterSpec);
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
    Comment comment = mapper.toComment(commentDto);
    var createCommentSpec = new CreateCommentSpec(cartoonId, userId, comment);
    Comment result = commentService.createComment(createCommentSpec);
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
    Comment updatedComment = mapper.toComment(commentDto);
    Comment result = commentService.updateComment(id, updatedComment);
    return ResponseEntity.ok(mapper.toDto(result));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long id) {
    Comment result = commentService.deleteComment(id);
    return ResponseEntity.ok(mapper.toDto(result));
  }

}
