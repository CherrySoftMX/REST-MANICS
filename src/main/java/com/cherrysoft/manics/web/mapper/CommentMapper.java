package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.web.dto.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  CommentDTO toDto(Comment comment);

  Comment toComment(CommentDTO commentDto);

}
