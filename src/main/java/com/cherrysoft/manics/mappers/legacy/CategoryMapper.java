package com.cherrysoft.manics.mappers.legacy;

import com.cherrysoft.manics.controller.legacy.request.CategoryRequest;
import com.cherrysoft.manics.model.legacy.core.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category categoryRequestToCategory(CategoryRequest categoryRequest);

}
