package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.controller.users.ManicUserController;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
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
public class ManicUserModelAssembler
    implements RepresentationModelAssembler<ManicUser, ManicUserDTO> {
  private final ManicUserMapper userMapper;
  private ManicUser entity;

  public ManicUserModelAssembler(ManicUserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  @NonNull
  public ManicUserDTO toModel(@NonNull ManicUser entity) {
    this.entity = entity;
    ManicUserDTO userModel = userMapper.toDto(entity);
    userModel.add(List.of(selfByIdLink(), selfByUsernameLink()));
    return userModel;
  }

  public Link selfByIdLink() {
    return withUpdateAndDeleteAffordances(
        linkTo(ManicUserController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  public Link selfByUsernameLink() {
    return withUpdateAndDeleteAffordances(
        linkTo(methodOn(ManicUserController.class)
            .getUserByUsername(null, entity.getUsername())).withSelfRel()
    );
  }

  public Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(ManicUserDTO.class)
        .withName("updateUser")
        .andAfford(HttpMethod.DELETE)
        .withOutput(ManicUserDTO.class)
        .withName("deleteUser")
        .toLink();
  }

}
