package com.manics.rest.mappers;

import com.manics.rest.model.core.Comment;
import com.manics.rest.rest.request.comment.CommentRequest;
import com.manics.rest.rest.request.comment.CommentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "story.id", source = "storyId")
  Comment commentRequestToComment(CommentRequest commentRequest);

  Comment commentUpdateRequestToComment(CommentUpdateRequest commentUpdateRequest);

}
