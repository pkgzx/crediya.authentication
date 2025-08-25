package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateTypeIdentificationDto;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITypeIdentificationMapper {

  @Mapping(target = "name", source = "dto.name")
  TypeIdentification toModel(CreateTypeIdentificationDto dto);
}
