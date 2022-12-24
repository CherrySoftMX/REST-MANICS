package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.web.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.model.BookmarkedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkedResultMapper {

  BookmarkedResultDTO toDto(BookmarkedResult bookmarkedResult);

}
