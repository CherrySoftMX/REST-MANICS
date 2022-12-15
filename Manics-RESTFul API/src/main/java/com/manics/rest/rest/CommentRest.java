package com.manics.rest.rest;

import com.manics.rest.mappers.CommentMapper;
import com.manics.rest.model.core.Comment;
import com.manics.rest.rest.request.comment.CommentRequest;
import com.manics.rest.rest.request.comment.CommentUpdateRequest;
import com.manics.rest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Deprecated
@RequestMapping("comentarios")
@AllArgsConstructor
public class CommentRest {
  private final CommentService commentService;
  private final CommentMapper commentMapper;

  @GetMapping
  public ResponseEntity<List<Comment>> getAllComments() {
    return ResponseEntity.ok(commentService.getAllComments());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Comment> getCommentById(@PathVariable(name = "id") Integer commentId) {
    return ResponseEntity.ok(commentService.getCommentById(commentId));
  }

  @GetMapping("/relatos/{id}")
  public ResponseEntity<List<Comment>> getCommentsByStoryId(@PathVariable(name = "id") Integer storyId) {
    return ResponseEntity.ok(commentService.getCommentsByStoryId(storyId));
  }

  @GetMapping("/usuarios/{id}")
  public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable(name = "id") Integer userId) {
    return ResponseEntity.ok(commentService.getCommentsByUserId(userId));
  }

  @PostMapping
  public ResponseEntity<Comment> createComment(
      @RequestBody @Valid CommentRequest commentRequest
  ) throws URISyntaxException {
    Comment comment = commentService.createComment(commentMapper.commentRequestToComment(commentRequest));
    return ResponseEntity
        .created(
            new URI(String.format("/comentarios/%d", comment.getId()))
        )
        .body(comment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Comment> updateComment(
      @PathVariable(name = "id") Integer commentId,
      @RequestBody @Valid CommentUpdateRequest commentRequest
  ) {
    return ResponseEntity.ok(
        commentService.updateComment(commentId, commentMapper.commentUpdateRequestToComment(commentRequest))
    );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Comment> deleteComment(@PathVariable(name = "id") Integer commentId) {
    return ResponseEntity.ok(commentService.deleteComment(commentId));
  }

}
