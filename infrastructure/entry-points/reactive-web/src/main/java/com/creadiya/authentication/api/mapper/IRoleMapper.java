package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    @Mapping(target = "permissions", expression = "java(toPermissionList(dto.getPermissionIds()))")
    Role toModel(CreateRoleDto dto);


    default List<Permission> toPermissionList(List<Integer> permissionIds) {
        if (permissionIds == null) {
            return null;
        }
        return permissionIds.stream()
                .map(this::toPermission)
                .collect(Collectors.toList());
    }

    default Permission toPermission(Integer permissionId) {
        if (permissionId == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setId(Long.valueOf(permissionId));
        return permission;
    }
}