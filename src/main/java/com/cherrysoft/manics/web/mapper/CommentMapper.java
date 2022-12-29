package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.model.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  CommentDTO toDto(Comment comment);

  List<CommentDTO> toDtoList(List<Comment> comments);

  Comment toComment(CommentDTO commentDto);

}
