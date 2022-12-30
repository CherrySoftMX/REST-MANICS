package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Category;
import com.cherrysoft.manics.web.controller.CategoryController;
import com.cherrysoft.manics.web.dto.CategoryDTO;
import com.cherrysoft.manics.web.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, CategoryDTO> {
  private final CategoryMapper mapper;
  private Category entity;

  @Override
  @NonNull
  public CategoryDTO toModel(@NonNull Category entity) {
    this.entity = entity;
    CategoryDTO categoryModel = mapper.toDto(entity);
    categoryModel.add(List.of(selfLink()));
    return categoryModel;
  }

  private Link selfLink() {
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(CategoryController.class)
            .getCategory(entity.getId()))
            .withSelfRel()
    );
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(CategoryDTO.class)
        .withName("updateCategory")
        .andAfford(HttpMethod.DELETE)
        .withOutput(CategoryDTO.class)
        .withName("deleteCategory")
        .toLink();
  }

}
