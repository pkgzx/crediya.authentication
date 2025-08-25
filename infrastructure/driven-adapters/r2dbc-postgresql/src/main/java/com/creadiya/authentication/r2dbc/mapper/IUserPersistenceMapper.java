package com.creadiya.authentication.r2dbc.mapper;

import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.r2dbc.entity.UserEntity;

public interface IUserPersistenceMapper {
  User toModel(UserEntity entity);
  UserEntity toEntity(User model);
}
