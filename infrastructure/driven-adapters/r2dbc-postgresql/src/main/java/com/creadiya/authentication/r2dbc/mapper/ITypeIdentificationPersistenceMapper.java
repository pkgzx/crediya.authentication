package com.creadiya.authentication.r2dbc.mapper;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.r2dbc.entity.TypeIdentificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITypeIdentificationPersistenceMapper {

  @Mapping(target = "id", source = "model.id")
  @Mapping(target = "name", source = "model.name")
  TypeIdentificationEntity toEntity(TypeIdentification model);

  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "name", source = "entity.name")
  TypeIdentification toModel(TypeIdentificationEntity entity);
}
