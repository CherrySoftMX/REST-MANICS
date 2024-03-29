package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.model.search.MatchingPage;
import com.cherrysoft.manics.web.controller.CartoonController;
import com.cherrysoft.manics.web.dto.search.MatchingPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class MatchingPageModelAssembler
    implements RepresentationModelAssembler<MatchingPage, MatchingPageDTO> {
  private final PageModelAssembler pageModelAssembler;
  private MatchingPage entity;

  @Override
  @NonNull
  public MatchingPageDTO toModel(@NonNull MatchingPage entity) {
    this.entity = entity;
    MatchingPageDTO model = new MatchingPageDTO();
    model.setPage(pageModelAssembler.toModel(entity.getPage()));
    model.setScore(entity.getScore());
    model.add(List.of(cartoonLink()));
    return model;
  }

  private Link cartoonLink() {
    Chapter chapter = entity.getPage().getChapter();
    return linkTo(methodOn(CartoonController.class)
        .getCartoonById(chapter.getCartoon().getId()))
        .withRel("cartoon");
  }

}
