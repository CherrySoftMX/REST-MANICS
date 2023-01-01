package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.security.utils.AuthenticationUtils;
import com.cherrysoft.manics.web.controller.BookmarkController;
import com.cherrysoft.manics.web.controller.CartoonController;
import com.cherrysoft.manics.web.controller.ChapterController;
import com.cherrysoft.manics.web.controller.LikeController;
import com.cherrysoft.manics.web.controller.search.SearchCartoonController;
import com.cherrysoft.manics.web.dto.BookmarkedResultDTO;
import com.cherrysoft.manics.web.dto.LikedResultDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonDTO;
import com.cherrysoft.manics.web.dto.cartoons.CartoonResponseDTO;
import com.cherrysoft.manics.web.mapper.CartoonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.QueryParameter;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.mediatype.ConfigurableAffordance;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.cherrysoft.manics.security.utils.AuthenticationUtils.currentLoggedUserIsAdmin;
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
    cartoonResponseModel.add(List.of(
        selfLink(),
        chaptersLink(),
        searchByQueryLink(),
        searchByImageLink()
    ));
    return cartoonResponseModel;
  }

  private Link selfLink() {
    return withCartoonAffordances(
        linkTo(CartoonController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  private Link chaptersLink() {
    return linkTo(methodOn(ChapterController.class)
        .getCartoonChapters(entity.getId(), null))
        .withRel("chapters");
  }

  private Link searchByQueryLink() {
    return linkTo(methodOn(SearchCartoonController.class)
        .searchCartoons(null, null))
        .withRel("searchByQuery");
  }

  private Link searchByImageLink() {
    return linkTo(methodOn(SearchCartoonController.class)
        .searchCartoonByImage(null, null))
        .withRel("searchByImage");
  }

  private Link withCartoonAffordances(Link link) {
    Long loggedUserId = AuthenticationUtils.getPrincipal().getId();
    ConfigurableAffordance configurableAffordance = Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("like")
        .withOutput(LikedResultDTO.class)
        .withTarget(
            linkTo(methodOn(LikeController.class)
                .like(null, entity.getId(), loggedUserId))
                .withSelfRel()
        )
        .withParameters(
            QueryParameter.required("cartoonId"),
            QueryParameter.required("userId")
        )

        .andAfford(HttpMethod.POST)
        .withName("bookmark")
        .withOutput(BookmarkedResultDTO.class)
        .withTarget(
            linkTo(methodOn(BookmarkController.class)
                .bookmark(null, entity.getId(), loggedUserId))
                .withSelfRel()
        ).withParameters(
            QueryParameter.required("cartoonId"),
            QueryParameter.required("userId")
        );

    if (currentLoggedUserIsAdmin()) {
      configurableAffordance = configurableAffordance
          .andAfford(HttpMethod.POST)
          .withName("createCartoon")
          .withInput(CartoonDTO.class)
          .withOutput(CartoonResponseDTO.class)
          .withTarget(linkTo(CartoonController.class).withSelfRel())

          .andAfford(HttpMethod.PATCH)
          .withName("updateCartoon")
          .withInput(CartoonDTO.class)
          .withOutput(CartoonResponseDTO.class)

          .andAfford(HttpMethod.DELETE)
          .withName("deleteCartoon")
          .withOutput(CartoonResponseDTO.class);
    }

    return configurableAffordance.toLink();
  }

}
