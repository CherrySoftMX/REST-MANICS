package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.core.Comment;
import com.cherrysoft.manics.rest.request.comment.CommentRequest;
import com.cherrysoft.manics.rest.request.comment.CommentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "story.id", source = "storyId")
  Comment commentRequestToComment(CommentRequest commentRequest);

  Comment commentUpdateRequestToComment(CommentUpdateRequest commentUpdateRequest);

}
