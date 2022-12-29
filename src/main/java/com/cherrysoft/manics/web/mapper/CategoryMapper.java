package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.web.dto.CategoryDTO;
import com.cherrysoft.manics.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category toCategory(CategoryDTO categoryDto);

  CategoryDTO toDto(Category category);

}
