package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.model.v2.BookmarkedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkedResultMapper {

  BookmarkedResultDTO toDto(BookmarkedResult bookmarkedResult);

}
