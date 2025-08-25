package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.dto.UpdateRoleDto;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements IRoleMapper {

  @Override
  public Role toModel(CreateRoleDto dto) {
    Role role = new Role();
    role.setName(dto.getName());
    role.setDescription(dto.getDescription());
    if (dto.getPermissionIds() != null) {
      role.setPermissions(dto.getPermissionIds().stream()
          .map(this::toPermission)
          .toList());
    }
    return role;
  }

  @Override
  public Role toModel(UpdateRoleDto dto, Integer id) {
    Role role = new Role();
    role.setId(Long.valueOf(id));
    role.setName(dto.getName());
    role.setDescription(dto.getDescription());
    if (dto.getPermissionIds() != null) {
      role.setPermissions(dto.getPermissionIds().stream()
          .map(this::toPermission)
          .toList());
    }
    return role;
  }

  private Permission toPermission(Integer permissionId) {
    Permission permission = new Permission();
    permission.setId(Long.valueOf(permissionId));
    return permission;
  }

}
