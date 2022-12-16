package com.cherrysoft.manics.service.legacy;

import com.cherrysoft.manics.exception.NotFoundException;
import com.cherrysoft.manics.model.legacy.core.Comment;
import com.cherrysoft.manics.model.legacy.core.Story;
import com.cherrysoft.manics.repository.CommentRepository;
import com.cherrysoft.manics.service.legacy.stories.StoryService;
import com.cherrysoft.manics.service.legacy.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserService userService;
  private final StoryService storyService;

  public List<Comment> getAllComments() {
    List<Comment> comments = new ArrayList<>();
    commentRepository.findAll().iterator().forEachRemaining(comments::add);
    return comments;
  }

  public Comment getCommentById(Integer commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new NotFoundException(
            String.format("No encontramos el comentario con el id: %d", commentId))
        );
  }

  public List<Comment> getCommentsByStoryId(Integer storyId) {
    return commentRepository.findByStory_Id(storyId);
  }

  public List<Comment> getCommentsByUserId(Integer userId) {
    return commentRepository.findByUserId(userId);
  }

  public Comment createComment(Comment comment) {
    userService.checkIfUserExist(comment.getUserId());
    Story story = storyService.getStoryById(comment.getStoryId());
    comment.setStory(story);
    return commentRepository.save(comment);
  }

  public Comment updateComment(Integer commentId, Comment newComment) {
    Comment comment = getCommentById(commentId);
    comment.setContent(newComment.getContent());
    return commentRepository.save(comment);
  }

  public Comment deleteComment(Integer commentId) {
    Comment comment = getCommentById(commentId);
    commentRepository.delete(comment);
    return comment;
  }

}
