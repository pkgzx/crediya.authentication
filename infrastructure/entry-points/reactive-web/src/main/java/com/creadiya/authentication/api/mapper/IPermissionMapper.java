package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreatePermissionDto;
import com.creadiya.authentication.model.permission.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
  @Mapping(target = "resource", source = "dto.resource")
  @Mapping(target = "action", source = "dto.action")
  Permission toModel(CreatePermissionDto dto);
}
