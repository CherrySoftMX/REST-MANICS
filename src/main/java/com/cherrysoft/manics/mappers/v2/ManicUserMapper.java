package com.cherrysoft.manics.mappers.v2;

import com.cherrysoft.manics.controller.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManicUserMapper {

  ManicUserDTO toDto(ManicUser user);

  ManicUser toManicUser(ManicUserDTO userDto);

}
