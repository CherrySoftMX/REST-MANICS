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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(PageController.class)
            .getPageById(entity.getId()))
            .withSelfRel()
    );
  }

  private Link chapterLink() {
    Chapter chapter = entity.getChapter();
    return linkTo(methodOn(ChapterController.class)
        .getChapterById(chapter.getId()))
        .withRel("chapter");
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(CartoonPageDTO.class)
        .withName("updatePage")
        .andAfford(HttpMethod.DELETE)
        .withOutput(CartoonPageDTO.class)
        .withName("deletePage")
        .toLink();
  }

}
