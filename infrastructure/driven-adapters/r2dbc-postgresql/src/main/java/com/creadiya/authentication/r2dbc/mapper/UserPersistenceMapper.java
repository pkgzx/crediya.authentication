package com.creadiya.authentication.r2dbc.mapper;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.r2dbc.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.UUID;

@Component
public class UserPersistenceMapper implements IUserPersistenceMapper {
  @Override
  public User toModel(UserEntity entity) {
    return new User.Builder()
      .id(UUID.fromString(entity.getId()))
        .email(entity.getEmail())
        .password(entity.getPassword())
      .name(entity.getName())
      .lastName(entity.getLastName())
      .phone(entity.getPhone())
      .address(entity.getAddress())
      .identification(entity.getIdentification())
      .birthday(entity.getBirthday())
      .typeIdentification(new TypeIdentification.Builder()
        .id(entity.getTypeIdentificationId())
        .build()
      )
      .role(new Role.Builder()
        .id(entity.getRoleId())
        .build()
      )
      .baseSalary(new BaseSalary.Builder()
        .currency(Currency.getInstance(entity.getBaseSalaryCurrency()))
        .value(entity.getBaseSalaryValue())
        .build()
      )
        .build();
  }

  @Override
  public UserEntity toEntity(User model) {
    return  UserEntity.builder()
      .id(UUID.randomUUID().toString())
      .email(model.getEmail())
      .password(model.getPassword())
      .name(model.getName())
      .lastName(model.getLastName())
      .phone(model.getPhone())
      .address(model.getAddress())
      .identification(model.getIdentification())
      .birthday(model.getBirthday())
      .typeIdentificationId(model.getTypeIdentification().getId())
      .roleId(model.getRole().getId())
      .baseSalaryCurrency(model.getBaseSalary().getCurrency().getCurrencyCode())
      .baseSalaryValue(model.getBaseSalary().getValue())
      .build();
  }
}
