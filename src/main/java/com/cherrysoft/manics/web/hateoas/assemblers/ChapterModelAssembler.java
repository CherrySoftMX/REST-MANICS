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

import static com.cherrysoft.manics.security.utils.AuthenticationUtils.currentLoggedUserIsAdmin;
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
    return withChapterAffordances(
        linkTo(ChapterController.class)
            .slash(entity.getId())
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
    return linkTo(CartoonController.class)
        .slash(cartoon.getId())
        .withRel("cartoon");
  }

  private Link withChapterAffordances(Link link) {
    if (!currentLoggedUserIsAdmin()) {
      return link;
    }
    return Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("createChapter")
        .withInput(ChapterDTO.class)
        .withOutput(ChapterResponseDTO.class)
        .withTarget(linkTo(ChapterController.class).withSelfRel())

        .andAfford(HttpMethod.PATCH)
        .withName("updateChapter")
        .withInput(ChapterDTO.class)
        .withOutput(ChapterResponseDTO.class)

        .andAfford(HttpMethod.DELETE)
        .withName("deleteChapter")
        .withOutput(ChapterResponseDTO.class)
        .toLink();
  }

}
