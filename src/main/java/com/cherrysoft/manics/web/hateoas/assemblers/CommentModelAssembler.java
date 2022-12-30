package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.Cartoon;
import com.cherrysoft.manics.model.Comment;
import com.cherrysoft.manics.web.controller.CommentController;
import com.cherrysoft.manics.web.controller.users.ManicUserController;
import com.cherrysoft.manics.web.dto.CommentDTO;
import com.cherrysoft.manics.web.hateoas.utils.CartoonLinkUtils;
import com.cherrysoft.manics.web.mapper.CommentMapper;
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
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(CommentController.class)
            .getCommentById(entity.getId()))
            .withSelfRel()
    );
  }

  private Link userLink() {
    return linkTo(methodOn(ManicUserController.class)
        .getUserById(null, entity.getUser().getId()))
        .withRel("user");
  }

  private Link cartoonLink() {
    Cartoon cartoon = entity.getCartoon();
    return CartoonLinkUtils.getCartoonLink(cartoon);
  }

  private Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(CommentDTO.class)
        .withName("updateComment")
        .andAfford(HttpMethod.DELETE)
        .withOutput(CommentDTO.class)
        .withName("deleteComment")
        .toLink();
  }

}
