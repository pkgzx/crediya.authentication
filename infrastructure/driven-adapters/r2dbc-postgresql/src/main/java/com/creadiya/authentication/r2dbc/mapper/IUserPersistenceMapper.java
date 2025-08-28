package com.creadiya.authentication.r2dbc.mapper;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Currency;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface IUserPersistenceMapper {

    @Mapping(target = "id", expression = "java(entity.getId() != null ? UUID.fromString(entity.getId()) : null)")
    @Mapping(target = "typeIdentification", source = "entity.typeIdentificationId", qualifiedByName = "mapTypeIdentification")
    @Mapping(target = "role", source = "entity.roleId", qualifiedByName = "mapRole")
    @Mapping(target = "baseSalary", source = "entity", qualifiedByName = "mapBaseSalary")
    User toModel(UserEntity entity);

  @Mapping(target = "id", expression = "java(model.getId() != null ? model.getId().toString() : UUID.randomUUID().toString())")
    @Mapping(target = "typeIdentificationId", source = "model.typeIdentification.id")
    @Mapping(target = "roleId", source = "model.role.id")
    @Mapping(target = "baseSalaryCurrency", source = "model.baseSalary.currency.currencyCode")
    @Mapping(target = "baseSalaryValue", source = "model.baseSalary.value")
    UserEntity toEntity(User model);

    @Named("mapTypeIdentification")
    default TypeIdentification mapTypeIdentification(Long id) {
        return id != null ? TypeIdentification.builder().id(id).build() : null;
    }

    @Named("mapRole")
    default Role mapRole(Long id) {
        return id != null ? Role.builder().id(id).build() : null;
    }

    @Named("mapBaseSalary")
    default BaseSalary mapBaseSalary(UserEntity entity) {
        if (entity.getBaseSalaryCurrency() == null || entity.getBaseSalaryValue() == null) {
            return null;
        }
        return BaseSalary.builder()
                .currency(Currency.getInstance(entity.getBaseSalaryCurrency()))
                .value(entity.getBaseSalaryValue())
                .build();
    }
}