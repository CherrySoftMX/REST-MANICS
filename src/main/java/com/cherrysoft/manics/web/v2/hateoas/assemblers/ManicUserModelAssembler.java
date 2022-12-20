package com.cherrysoft.manics.web.v2.hateoas.assemblers;

import com.cherrysoft.manics.model.v2.auth.ManicUser;
import com.cherrysoft.manics.web.v2.controller.users.ManicUserController;
import com.cherrysoft.manics.web.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.v2.mapper.v2.ManicUserMapper;
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
            .getUserByUsername(null, entity.getUsername())).withSelfRel()
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
        .updateUser(null, entity.getId(), null)).withRel("selfUpdate");
  }

  public Link selfDeleteLink() {
    return linkTo(methodOn(ManicUserController.class)
        .deleteUser(null, entity.getId())).withRel("selfDelete");
  }

}
