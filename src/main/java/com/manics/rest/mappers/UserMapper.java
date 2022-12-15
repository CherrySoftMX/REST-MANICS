package com.manics.rest.mappers;

import com.manics.rest.model.auth.User;
import com.manics.rest.rest.request.user.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User userRequestToUser(UserRequest userRequest);

}
