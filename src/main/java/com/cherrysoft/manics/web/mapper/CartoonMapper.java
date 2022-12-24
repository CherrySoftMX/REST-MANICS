package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {ChapterMapper.class}
)
public interface CartoonMapper {

  @Mappings({
      @Mapping(target = "baseCartoonFields.name", source = "name"),
      @Mapping(target = "baseCartoonFields.author", source = "author"),
      @Mapping(target = "baseCartoonFields.availableChapters", source = "availableChapters"),
      @Mapping(target = "baseCartoonFields.publicationYear", source = "publicationYear"),
  })
  CartoonDTO toDto(Cartoon cartoon);

  @Mappings({
      @Mapping(target = "baseCartoonFields.name", source = "name"),
      @Mapping(target = "baseCartoonFields.author", source = "author"),
      @Mapping(target = "baseCartoonFields.availableChapters", source = "availableChapters"),
      @Mapping(target = "baseCartoonFields.publicationYear", source = "publicationYear"),
  })
  CartoonResponseDTO toResponseDto(Cartoon cartoon);

  List<CartoonResponseDTO> toReponseDtoList(List<Cartoon> cartoons);

  @InheritInverseConfiguration
  Cartoon toCartoon(CartoonDTO cartoonDto);

}
