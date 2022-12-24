package com.cherrysoft.manics.web.mapper;

import com.cherrysoft.manics.model.auth.ManicUser;
import com.cherrysoft.manics.web.dto.users.ManicUserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManicUserMapper {

  ManicUserDTO toDto(ManicUser user);

  List<ManicUserDTO> toDtoList(List<ManicUser> users);

  ManicUser toManicUser(ManicUserDTO userDto);

}
