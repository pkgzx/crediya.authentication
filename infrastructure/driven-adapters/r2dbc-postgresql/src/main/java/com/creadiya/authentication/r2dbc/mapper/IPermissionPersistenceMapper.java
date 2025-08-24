package com.creadiya.authentication.r2dbc.mapper;


import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPermissionPersistenceMapper {
  @Mapping(target = "resource", source = "model.resource")
  @Mapping(target = "action", source = "model.action")
  PermissionEntity toEntity(Permission model);

  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "resource", source = "entity.resource")
  @Mapping(target = "action", source = "entity.action")
  Permission toModel(PermissionEntity entity);
}
