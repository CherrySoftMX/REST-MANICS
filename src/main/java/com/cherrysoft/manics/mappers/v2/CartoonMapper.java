package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.controller.v2.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.model.v2.Cartoon;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartoonMapper {

  @Mappings({
      @Mapping(target = "baseCartoonFields.id", source = "id"),
      @Mapping(target = "baseCartoonFields.name", source = "name"),
      @Mapping(target = "baseCartoonFields.author", source = "author"),
      @Mapping(target = "baseCartoonFields.availableChapters", source = "availableChapters"),
      @Mapping(target = "baseCartoonFields.publicationYear", source = "publicationYear"),
  })
  CartoonDTO toDto(Cartoon cartoon);

  @Mappings({
      @Mapping(target = "baseCartoonFields.id", source = "id"),
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
