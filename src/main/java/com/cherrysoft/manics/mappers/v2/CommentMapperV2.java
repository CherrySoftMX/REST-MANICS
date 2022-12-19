package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.web.v2.dto.CommentDTO;
import com.cherrysoft.manics.model.v2.CommentV2;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapperV2 {

  CommentDTO toDto(CommentV2 comment);

  List<CommentDTO> toDtoList(List<CommentV2> comments);

  CommentV2 toComment(CommentDTO commentDto);

}
