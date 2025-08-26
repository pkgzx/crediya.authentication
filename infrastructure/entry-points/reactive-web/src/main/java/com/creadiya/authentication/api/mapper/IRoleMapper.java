package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.dto.UpdateRoleDto;
import com.creadiya.authentication.model.role.Role;


public interface IRoleMapper {
  Role toModel(CreateRoleDto dto);
  Role toModel(UpdateRoleDto dto, Integer id);
}
