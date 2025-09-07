package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.ResponseValidateTokenDto;
import com.creadiya.authentication.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAuthMapper {

  @Mapping(target = "email", source = "email")
  @Mapping(target = "identification", source = "identification")
  @Mapping(target = "role", source = "user.role.name")
  ResponseValidateTokenDto toResponse(User user);
}
