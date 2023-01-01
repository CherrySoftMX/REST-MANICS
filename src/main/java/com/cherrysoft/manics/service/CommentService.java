package com.cherrysoft.manics.service;

import com.cherrysoft.manics.exception.AmbiguousFilterException;
import com.cherrysoft.manics.exception.CommentNotFoundException;
import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.model.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.specs.CreateCommentSpec;
import com.cherrysoft.manics.repository.CommentRepository;
import com.cherrysoft.manics.service.users.ManicUserService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final ManicUserService userService;
  private final CartoonService cartoonService;
  private final CommentRepository commentRepository;

  public Page<Comment> searchCommentsByContent(String content, Pageable pageable) {
    return commentRepository.searchCommentByContent(content, pageable);
  }

  public Comment getCommentById(Long id) {
    return commentRepository
        .findById(id)
        .orElseThrow(() -> new CommentNotFoundException(id));
  }

  public Page<Comment> getComments(CommentFilterSpec filterSpec) {
    if (filterSpec.ambiguousFiltering()) {
      throw new AmbiguousFilterException("Either userId or cartoonId (NOT both) MUST be provided.");
    }
    if (filterSpec.filterByCommentsOfUser()) {
      return getCommentsOfUser(filterSpec);
    }
    if (filterSpec.filterByCommentsOfCartoon()) {
      return getCommentsOfCartoon(filterSpec);
    }
    if (filterSpec.filterByCommentsOfComment()) {
      return getCommentsOfComment(filterSpec);
    }
    return Page.empty();
  }

  public Page<Comment> getCommentsOfCartoon(CommentFilterSpec spec) {
    return commentRepository.findCommentsByCartoon_Id(spec.getCartoonId(), spec.getPageable());
  }

  public Page<Comment> getCommentsOfUser(CommentFilterSpec spec) {
    return commentRepository.findCommentsByUser_Id(spec.getUserId(), spec.getPageable());
  }

  public Page<Comment> getCommentsOfComment(CommentFilterSpec spec) {
    return commentRepository.findCommentsByParent_Id(spec.getCommentId(), spec.getPageable());
  }

  public Comment createComment(CreateCommentSpec spec) {
    ManicUser userRef = userService.getUserReferenceById(spec.getUserId());
    Cartoon cartoonRef = cartoonService.getCartonReferenceById(spec.getCartoonId());
    Comment newComment = spec.getNewComment();
    newComment.setUser(userRef);
    newComment.setCartoon(cartoonRef);
    if (spec.parentIdProvided()) {
      Comment parentCommentRef = commentRepository.getReferenceById(spec.getParentId());
      newComment.setParent(parentCommentRef);
    }
    return commentRepository.save(newComment);
  }

  public Comment updateComment(Long id, Comment updatedComment) {
    Comment comment = getCommentById(id);
    BeanUtils.copyProperties(updatedComment, comment);
    return commentRepository.save(comment);
  }

  public Comment deleteComment(Long id) {
    Comment comment = getCommentById(id);
    commentRepository.deleteById(id);
    return comment;
  }

}
