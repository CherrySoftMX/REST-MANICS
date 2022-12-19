package com.cherrysoft.manics.web.v2.mapper.v2;

import com.cherrysoft.manics.web.v2.dto.pages.PageDTO;
import com.cherrysoft.manics.model.v2.PageV2;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapperV2 {

  PageDTO toDto(PageV2 page);

  List<PageDTO> toDtoList(List<PageV2> page);

  PageV2 toPage(PageDTO pageDto);

}
