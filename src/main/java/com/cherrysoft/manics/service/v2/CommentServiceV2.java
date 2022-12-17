package com.cherrysoft.manics.service.v2;

import com.cherrysoft.manics.exception.v2.AmbiguousFilterException;
import com.cherrysoft.manics.exception.v2.CommentNotFoundException;
import com.cherrysoft.manics.model.v2.Cartoon;
import com.cherrysoft.manics.model.v2.CommentV2;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.model.v2.specs.CommentFilterSpec;
import com.cherrysoft.manics.model.v2.specs.CreateCommentSpec;
import com.cherrysoft.manics.repository.v2.CommentRepositoryV2;
import com.cherrysoft.manics.service.v2.users.ManicUserService;
import com.cherrysoft.manics.util.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceV2 {
  private final ManicUserService userService;
  private final CartoonService cartoonService;
  private final CommentRepositoryV2 commentRepository;

  public CommentV2 getCommentById(Long id) {
    return commentRepository
        .findById(id)
        .orElseThrow(() -> new CommentNotFoundException(id));
  }

  public List<CommentV2> getComments(CommentFilterSpec filterSpec) {
    if (filterSpec.ambiguousFiltering()) {
      throw new AmbiguousFilterException("Either userId or cartoonId (NOT both) MUST be provided.");
    }
    if (filterSpec.filterByUserComments()) {
      return getUserComments(filterSpec.getUserId());
    }
    if (filterSpec.filterByCartoonComments()) {
      return getCartoonComments(filterSpec.getCartoonId());
    }
    return List.of();
  }

  public List<CommentV2> getCartoonComments(Long cartoonId) {
    return commentRepository.findCommentV2sByCartoon_Id(cartoonId);
  }

  public List<CommentV2> getUserComments(Long userId) {
    return commentRepository.findCommentV2sByUser_Id(userId);
  }

  public CommentV2 createComment(CreateCommentSpec spec) {
    ManicUser userReference = userService.getUserReferenceById(spec.getUserId());
    Cartoon cartoonReference = cartoonService.getCartonReferenceById(spec.getCartoonId());
    CommentV2 newComment = spec.getNewComment();
    newComment.setUser(userReference);
    newComment.setCartoon(cartoonReference);
    return commentRepository.save(newComment);
  }

  public CommentV2 updateComment(Long id, CommentV2 updatedComment) {
    CommentV2 comment = getCommentById(id);
    BeanUtils.copyProperties(updatedComment, comment);
    return commentRepository.save(comment);
  }

  public CommentV2 deleteComment(Long id) {
    CommentV2 comment = getCommentById(id);
    commentRepository.deleteById(id);
    return comment;
  }

}
