package com.cherrysoft.manics.web.v2.mapper.legacy;

import com.cherrysoft.manics.web.legacy.request.CategoryRequest;
import com.cherrysoft.manics.model.legacy.core.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category categoryRequestToCategory(CategoryRequest categoryRequest);

}
