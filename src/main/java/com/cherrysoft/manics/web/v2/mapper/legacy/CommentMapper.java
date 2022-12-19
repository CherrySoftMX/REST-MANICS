package com.cherrysoft.manics.web.v2.mapper.legacy;

import com.cherrysoft.manics.model.legacy.core.Comment;
import com.cherrysoft.manics.web.legacy.request.comment.CommentRequest;
import com.cherrysoft.manics.web.legacy.request.comment.CommentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  @Mapping(target = "story.id", source = "storyId")
  Comment commentRequestToComment(CommentRequest commentRequest);

  Comment commentUpdateRequestToComment(CommentUpdateRequest commentUpdateRequest);

}
