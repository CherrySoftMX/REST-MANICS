package com.cherrysoft.manics.web.v2.mapper.v2;

import com.cherrysoft.manics.web.v2.dto.users.ManicUserDTO;
import com.cherrysoft.manics.model.v2.auth.ManicUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManicUserMapper {

  ManicUserDTO toDto(ManicUser user);

  List<ManicUserDTO> toDtoList(List<ManicUser> users);

  ManicUser toManicUser(ManicUserDTO userDto);

}
