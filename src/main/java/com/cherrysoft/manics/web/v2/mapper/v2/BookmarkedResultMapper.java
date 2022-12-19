package com.cherrysoft.manics.web.v2.mapper.v2;

import com.cherrysoft.manics.web.v2.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.model.v2.BookmarkedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkedResultMapper {

  BookmarkedResultDTO toDto(BookmarkedResult bookmarkedResult);

}
