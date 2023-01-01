package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {ChapterMapper.class}
)
public interface CartoonMapper {

  CartoonDTO toDto(Cartoon cartoon);

  CartoonResponseDTO toResponseDto(Cartoon cartoon);

  Cartoon toCartoon(CartoonDTO cartoonDto);

}
