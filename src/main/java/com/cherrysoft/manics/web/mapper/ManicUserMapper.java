package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManicUserMapper {

  ManicUserDTO toDto(ManicUser user);

  ManicUser toManicUser(ManicUserDTO userDto);

}
