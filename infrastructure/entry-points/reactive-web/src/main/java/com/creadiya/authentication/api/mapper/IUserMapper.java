package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.model.user.User;

public interface IUserMapper {
  User toModel(CreateUserDto dto);
}
