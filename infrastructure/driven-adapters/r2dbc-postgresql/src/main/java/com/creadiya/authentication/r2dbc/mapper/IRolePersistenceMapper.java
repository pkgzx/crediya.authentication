package com.creadiya.authentication.r2dbc.mapper;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.r2dbc.entity.RoleEntity;
import com.creadiya.authentication.r2dbc.entity.RolePermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRolePersistenceMapper {
  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "name", source = "entity.name")
  @Mapping(target = "description", source = "entity.description")
  Role toModel(RoleEntity entity);

  @Mapping(target = "id", source = "model.id")
  @Mapping(target = "name", source = "model.name")
  @Mapping(target = "description", source = "model.description")
  RoleEntity toEntity(Role model);

  @Mapping(target = "roleId", source = "roleId")
  @Mapping(target = "permissionId", source = "permissionId")
  RolePermissionEntity toRolePermissionEntity(Long roleId, Long permissionId);
}
