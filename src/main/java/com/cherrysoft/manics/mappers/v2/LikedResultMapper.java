package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.LikedResultDTO;
import com.cherrysoft.manics.model.v2.LikedResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikedResultMapper {

  LikedResultDTO toDto(LikedResult likedResult);

}
