package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.usecase.permission.enums.StringConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Component
public class UserMapper implements IUserMapper {
  @Override
  public User toModel(CreateUserDto dto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConstants.DATE_PATTERN.getValue());
    String currencyCode = dto.getBaseSalary().getCurrency();

    if (currencyCode == null || currencyCode.length() != 3) {
      throw new BusinessException(TechnicalMessage.INVALID_CURRENCY);
    }

    return new User.Builder()
      .email(dto.getEmail())
      .name(dto.getName())
      .password(dto.getPassword())
      .birthday(LocalDate.parse(dto.getBirthday(), formatter))
      .lastName(dto.getLastName())
      .identification(dto.getIdentification())
      .phone(dto.getPhone())
      .address(dto.getAddress())
      .role(
        new Role.Builder()
          .id(dto.getRoleId())
          .build()
      )
      .typeIdentification(
        new TypeIdentification.Builder()
          .id(dto.getTypeIdentificationId())
          .build()
      )
      .baseSalary(new BaseSalary.Builder()
      .currency(Currency.getInstance(dto.getBaseSalary().getCurrency()))
        .value(dto.getBaseSalary().getValue())
        .build()
      )
      .build();
  }
}
