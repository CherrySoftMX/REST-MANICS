package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Chapter;
import com.cherrysoft.manics.web.controller.CartoonController;
import com.cherrysoft.manics.web.controller.ChapterController;
import com.cherrysoft.manics.web.controller.PageController;
import com.cherrysoft.manics.web.dto.chapters.ChapterDTO;
import com.cherrysoft.manics.web.dto.chapters.ChapterResponseDTO;
import com.cherrysoft.manics.web.mapper.ChapterMapper;
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
public class ChapterModelAssembler implements RepresentationModelAssembler<Chapter, ChapterResponseDTO> {
  private final ChapterMapper mapper;
  private Chapter entity;

  @Override
  @NonNull
  public ChapterResponseDTO toModel(@NonNull Chapter entity) {
    this.entity = entity;
    ChapterResponseDTO chapterResponseModel = mapper.toResponseDto(entity);
    chapterResponseModel.add(List.of(selfLink(), pagesLink(), cartoonLink()));
    return chapterResponseModel;
  }

  private Link selfLink() {
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(ChapterController.class)
            .getChapterById(entity.getId()))
            .withSelfRel()
    );
  }

  private Link pagesLink() {
    return linkTo(methodOn(PageController.class)
        .getChapterPages(entity.getId(), null))
        .withRel("pages");
  }

  private Link cartoonLink() {
    Cartoon cartoon = entity.getCartoon();
    return linkTo(methodOn(CartoonController.class)
        .getCartoonById(cartoon.getId()))
        .withRel("cartoon");
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInput(ChapterDTO.class)
        .withOutput(ChapterResponseDTO.class)
        .withName("updateChapter")
        .andAfford(HttpMethod.DELETE)
        .withOutput(ChapterDTO.class)
        .withName("deleteChapter")
        .toLink();
  }

}
