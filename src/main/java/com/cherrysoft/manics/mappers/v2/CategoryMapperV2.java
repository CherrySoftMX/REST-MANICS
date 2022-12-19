package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.web.v2.dto.CategoryDTO;
import com.cherrysoft.manics.model.v2.CategoryV2;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapperV2 {

  CategoryV2 toCategory(CategoryDTO categoryDto);

  CategoryDTO toDto(CategoryV2 category);

}
