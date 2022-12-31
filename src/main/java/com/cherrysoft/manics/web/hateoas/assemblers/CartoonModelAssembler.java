package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.web.controller.CartoonController;
import com.cherrysoft.manics.web.controller.ChapterController;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
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
public class CartoonModelAssembler implements RepresentationModelAssembler<Cartoon, CartoonResponseDTO> {
  private final CartoonMapper mapper;
  protected Cartoon entity;

  @Override
  @NonNull
  public CartoonResponseDTO toModel(@NonNull Cartoon entity) {
    this.entity = entity;
    CartoonResponseDTO cartoonResponseModel = mapper.toResponseDto(entity);
    cartoonResponseModel.add(List.of(selfLink(), chaptersLink()));
    return cartoonResponseModel;
  }

  private Link selfLink() {
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(CartoonController.class)
            .getCartoonById(entity.getId()))
            .withSelfRel()
    );
  }

  private Link chaptersLink() {
    return linkTo(methodOn(ChapterController.class)
        .getCartoonChapters(entity.getId(), null))
        .withRel("chapters");
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInput(CartoonDTO.class)
        .withOutput(CartoonResponseDTO.class)
        .withName("updateCartoon")
        .andAfford(HttpMethod.DELETE)
        .withOutput(CartoonResponseDTO.class)
        .withName("deleteCartoon")
        .toLink();
  }

}
