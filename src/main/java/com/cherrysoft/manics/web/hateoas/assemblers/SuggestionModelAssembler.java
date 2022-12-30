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
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(SuggestionController.class)
            .getSuggestion(null, entity.getId(), user.getId()))
            .withSelfRel()
    );
  }

  private Link userLink() {
    ManicUser user = entity.getUser();
    return linkTo(methodOn(ManicUserController.class)
        .getUserById(null, user.getId()))
        .withRel("user");
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(SuggestionDTO.class)
        .withName("updateSuggestion")
        .andAfford(HttpMethod.DELETE)
        .withOutput(SuggestionDTO.class)
        .withName("deleteSuggestion")
        .toLink();
  }

}
