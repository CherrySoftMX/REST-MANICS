package com.manics.rest.mappers;

import com.manics.rest.model.core.Category;
import com.manics.rest.rest.request.CategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category categoryRequestToCategory(CategoryRequest categoryRequest);

}
