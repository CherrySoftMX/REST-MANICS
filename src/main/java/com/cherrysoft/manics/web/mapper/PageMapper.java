package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageMapper {

  CartoonPageDTO toDto(CartoonPage page);

  CartoonPage toPage(CartoonPageDTO pageDto);

}
