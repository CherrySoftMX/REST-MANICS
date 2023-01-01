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

import static com.cherrysoft.manics.security.utils.AuthenticationUtils.currentLoggedUserIsAdmin;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    return withCategoryAffordances(
        linkTo(CategoryController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  private Link withCategoryAffordances(Link link) {
    if (!currentLoggedUserIsAdmin()) {
      return link;
    }
    return Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("createCategory")
        .withInputAndOutput(CategoryDTO.class)
        .withTarget(linkTo(CategoryController.class).withSelfRel())

        .andAfford(HttpMethod.PATCH)
        .withName("updateCategory")
        .withInputAndOutput(CategoryDTO.class)

        .andAfford(HttpMethod.DELETE)
        .withName("deleteCategory")
        .withOutput(CategoryDTO.class)
        .toLink();
  }

}
