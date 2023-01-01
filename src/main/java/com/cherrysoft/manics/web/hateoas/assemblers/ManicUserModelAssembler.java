package com.cherrysoft.manics.web.hateoas.assemblers;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.controller.users.ManicUserController;
import com.cherrysoft.manics.web.controller.users.ManicUserRoleController;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import com.cherrysoft.manics.web.dto.users.ManicUserRoleSetDTO;
import com.cherrysoft.manics.web.mapper.ManicUserMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.hateoas.mediatype.ConfigurableAffordance;
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

  private Link selfByIdLink() {
    return withUserAffordances(
        linkTo(ManicUserController.class)
            .slash(entity.getId())
            .withSelfRel()
    );
  }

  private Link selfByUsernameLink() {
    return linkTo(methodOn(ManicUserController.class)
        .getUserByUsername(null, entity.getUsername()))
        .withSelfRel();
  }

  private Link withUserAffordances(Link link) {
    ConfigurableAffordance configurableAffordance = Affordances.of(link)
        .afford(HttpMethod.PATCH)
        .withName("updateUser")
        .withInputAndOutput(ManicUserDTO.class)
        .andAfford(HttpMethod.DELETE)
        .withName("deleteUser")
        .withOutput(ManicUserDTO.class);

    if (entity.isAdmin()) {
      configurableAffordance = configurableAffordance
          .andAfford(HttpMethod.POST)
          .withInputAndOutput(ManicUserDTO.class)
          .withTarget(linkTo(ManicUserController.class).withSelfRel())
          .withName("createUser")

          .andAfford(HttpMethod.PUT)
          .withName("addUserRoles")
          .withInput(ManicUserRoleSetDTO.class)
          .withOutput(ManicUserDTO.class)
          .withTarget(
              linkTo(methodOn(ManicUserRoleController.class)
                  .removeUserRoles(entity.getId(), null))
                  .withSelfRel()
          )

          .andAfford(HttpMethod.DELETE)
          .withName("removeUserRoles")
          .withInput(ManicUserRoleSetDTO.class)
          .withOutput(ManicUserDTO.class)
          .withTarget(
              linkTo(methodOn(ManicUserRoleController.class)
                  .addUserRoles(entity.getId(), null))
                  .withSelfRel()
          );
    }

    return configurableAffordance.toLink();
  }

}
