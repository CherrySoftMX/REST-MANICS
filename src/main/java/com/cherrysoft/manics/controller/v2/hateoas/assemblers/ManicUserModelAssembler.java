package com.cherrysoft.manics.controller.v2.hateoas.assemblers;

import com.cherrysoft.manics.controller.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.controller.v2.users.ManicUserController;
import com.cherrysoft.manics.mappers.v2.ManicUserMapper;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManicUserModelAssembler
    extends RepresentationModelAssemblerSupport<ManicUser, ManicUserDTO> {
  private final ManicUserMapper userMapper;
  private ManicUser entity;

  public ManicUserModelAssembler(ManicUserMapper userMapper) {
    super(ManicUserController.class, ManicUserDTO.class);
    this.userMapper = userMapper;
  }

  @Override
  @NonNull
  public ManicUserDTO toModel(@NonNull ManicUser entity) {
    this.entity = entity;
    ManicUserDTO userModel = userMapper.toDto(entity);
    userModel.add(List.of(
        selfByIdLink(),
        selfByUsernameLink(),
        selfUpdateLink(),
        selfDeleteLink())
    );
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
            .getUserByUsername(entity.getUsername())).withSelfRel()
    );
  }

  public Link withUpdateAndDeleteAffordances(Link link) {
    return Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withInputAndOutput(ManicUserDTO.class)
        .withName("updateUser")
        .andAfford(HttpMethod.DELETE)
        .withName("deleteUser")
        .withOutput(ManicUserDTO.class)
        .toLink();
  }

  public Link selfUpdateLink() {
    return linkTo(methodOn(ManicUserController.class)
        .updateUser(entity.getId(), null)).withRel("selfUpdate");
  }

  public Link selfDeleteLink() {
    return linkTo(methodOn(ManicUserController.class)
        .deleteUser(entity.getId())).withRel("selfDelete");
  }

}