package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.web.dto.LikedResultDTO;
import com.cherrysoft.manics.model.LikedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikedResultMapper {

  LikedResultDTO toDto(LikedResult likedResult);

}
