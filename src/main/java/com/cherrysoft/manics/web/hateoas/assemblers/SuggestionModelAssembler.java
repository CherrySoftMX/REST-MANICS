package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Suggestion;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.controller.SuggestionController;
import com.cherrysoft.manics.web.controller.users.ManicUserController;
import com.cherrysoft.manics.web.dto.SuggestionDTO;
import com.cherrysoft.manics.web.mapper.SuggestionMapper;
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
public class SuggestionModelAssembler implements RepresentationModelAssembler<Suggestion, SuggestionDTO> {
  private final SuggestionMapper mapper;
  private Suggestion entity;

  @Override
  @NonNull
  public SuggestionDTO toModel(@NonNull Suggestion entity) {
    this.entity = entity;
    SuggestionDTO suggestionModel = mapper.toDto(entity);
    suggestionModel.add(List.of(selfLink(), userLink()));
    return suggestionModel;
  }

  private Link selfLink() {
    ManicUser user = entity.getUser();
    return withSuggestionAffordances(
        linkTo(methodOn(SuggestionController.class)
            .getSuggestion(null, entity.getId(), user.getId()))
            .withSelfRel()
    );
  }

  private Link userLink() {
    ManicUser user = entity.getUser();
    return linkTo(ManicUserController.class)
        .slash(user.getId())
        .withRel("user");
  }

  private Link withSuggestionAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("createSuggestion")
        .withInputAndOutput(SuggestionDTO.class)
        .withTarget(linkTo(SuggestionController.class).withSelfRel())

        .andAfford(HttpMethod.PATCH)
        .withName("updateSuggestion")
        .withInputAndOutput(SuggestionDTO.class)

        .andAfford(HttpMethod.DELETE)
        .withName("deleteSuggestion")
        .withOutput(SuggestionDTO.class)
        .toLink();
  }

}
