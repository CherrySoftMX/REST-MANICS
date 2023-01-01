package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.controller.CartoonController;
import com.cherrysoft.manics.web.controller.CommentController;
import com.cherrysoft.manics.web.controller.users.ManicUserController;
import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.web.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.mediatype.ConfigurableAffordance;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.cherrysoft.manics.security.utils.AuthenticationUtils.currentLoggedUserIsAdmin;
import static com.cherrysoft.manics.security.utils.AuthenticationUtils.sameUserAsLogged;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, CommentDTO> {
  private final CommentMapper mapper;
  private Comment entity;

  @Override
  @NonNull
  public CommentDTO toModel(@NonNull Comment entity) {
    this.entity = entity;
    CommentDTO commentModel = mapper.toDto(entity);
    commentModel.add(List.of(selfLink(), userLink(), cartoonLink()));
    return commentModel;
  }

  private Link selfLink() {
    return withCommentAffordances(
        linkTo(CommentController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  private Link userLink() {
    ManicUser user = entity.getUser();
    return linkTo(ManicUserController.class)
        .slash(user.getId())
        .withRel("user");
  }

  private Link cartoonLink() {
    Cartoon cartoon = entity.getCartoon();
    return linkTo(CartoonController.class)
        .slash(cartoon.getId())
        .withRel("cartoon");
  }

  private Link withCommentAffordances(Link link) {
    ConfigurableAffordance configurableAffordance = Affordances.of(link)
        .afford(HttpMethod.POST)
        .withName("createComment")
        .withInputAndOutput(CommentDTO.class)
        .withTarget(linkTo(CommentController.class).withSelfRel());

    if (sameUserAsLogged(entity.getUser())) {
      configurableAffordance = configurableAffordance
          .andAfford(HttpMethod.PATCH)
          .withName("updateComment")
          .withInputAndOutput(CommentDTO.class);
    }

    if (currentLoggedUserIsAdmin()) {
      configurableAffordance = configurableAffordance
          .andAfford(HttpMethod.DELETE)
          .withName("deleteComment")
          .withOutput(CommentDTO.class);
    }

    return configurableAffordance.toLink();
  }

}
