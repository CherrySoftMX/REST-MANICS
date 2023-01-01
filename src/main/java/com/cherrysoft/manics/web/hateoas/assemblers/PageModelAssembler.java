package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.web.controller.ChapterController;
import com.cherrysoft.manics.web.controller.PageController;
import com.cherrysoft.manics.web.dto.pages.CartoonPageDTO;
import com.cherrysoft.manics.web.mapper.PageMapper;
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
public class PageModelAssembler implements RepresentationModelAssembler<CartoonPage, CartoonPageDTO> {
  private final PageMapper mapper;
  private CartoonPage entity;

  @Override
  @NonNull
  public CartoonPageDTO toModel(@NonNull CartoonPage entity) {
    this.entity = entity;
    CartoonPageDTO pageModel = mapper.toDto(entity);
    pageModel.add(List.of(selfLink(), chapterLink()));
    return pageModel;
  }

  private Link selfLink() {
    return withPageAffordances(
        linkTo(PageController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  private Link chapterLink() {
    Chapter chapter = entity.getChapter();
    return linkTo(ChapterController.class)
        .slash(chapter.getId())
        .withRel("chapter");
  }

  private Link withPageAffordances(Link link) {
    if (!currentLoggedUserIsAdmin()) {
      return link;
    }
    return Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("createPage")
        .withInputAndOutput(CartoonPageDTO.class)
        .withTarget(linkTo(PageController.class).withSelfRel())

        .andAfford(HttpMethod.PATCH)
        .withInputAndOutput(CartoonPageDTO.class)
        .withName("updatePage")

        .andAfford(HttpMethod.DELETE)
        .withOutput(CartoonPageDTO.class)
        .withName("deletePage")
        .toLink();
  }

}
