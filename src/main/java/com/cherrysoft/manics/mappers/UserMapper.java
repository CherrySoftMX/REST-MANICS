package com.cherrysoft.manics.mappers;

import com.cherrysoft.manics.model.legacy.auth.User;
import com.cherrysoft.manics.rest.request.user.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User userRequestToUser(UserRequest userRequest);

}
