package com.cherrysoft.manics.web.hateoas.processors;

import com.cherrysoft.manics.web.controller.CategoryController;
import com.cherrysoft.manics.web.controller.CommentController;
import com.cherrysoft.manics.web.controller.LikeController;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartoonModelProcessor implements RepresentationModelProcessor<CartoonResponseDTO> {
  private CartoonResponseDTO model;

  @Override
  @NonNull
  public CartoonResponseDTO process(@NonNull CartoonResponseDTO model) {
    this.model = model;
    model.add(List.of(commentsLink(), categoriesLink(), likedByLink()));
    return model;
  }

  private Link commentsLink() {
    Map<String, String> params = Map.of("cartoonId", model.getId().toString());
    return linkTo(methodOn(CommentController.class)
        .getComments(params, null))
        .withRel("comments");
  }

  private Link categoriesLink() {
    return linkTo(methodOn(CategoryController.class)
        .getCategoriesOfCartoon(model.getId(), null))
        .withRel("categories");
  }

  private Link likedByLink() {
    return linkTo(methodOn(LikeController.class)
        .getLikedBy(model.getId(), null))
        .withRel("likedBy");
  }

}
