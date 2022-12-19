package com.cherrysoft.manics.web.v2.mapper.legacy;

import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.web.legacy.request.user.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User userRequestToUser(UserRequest userRequest);

}
