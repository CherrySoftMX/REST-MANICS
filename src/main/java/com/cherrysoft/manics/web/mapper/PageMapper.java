package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.web.dto.pages.PageDTO;
import com.cherrysoft.manics.model.Page;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapper {

  PageDTO toDto(Page page);

  List<PageDTO> toDtoList(List<Page> page);

  Page toPage(PageDTO pageDto);

}
