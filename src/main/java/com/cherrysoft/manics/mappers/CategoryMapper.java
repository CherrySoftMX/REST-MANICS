package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.legacy.core.Category;
import com.cherrysoft.manics.controller.legacy.request.CategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category categoryRequestToCategory(CategoryRequest categoryRequest);

}
