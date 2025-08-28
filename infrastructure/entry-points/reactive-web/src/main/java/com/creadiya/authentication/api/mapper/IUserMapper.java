package com.creadiya.authentication.api.mapper;

import com.creadiya.authentication.api.dto.CreateBaseSalaryDto;
import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.usecase.permission.enums.StringConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Mapper(componentModel = "spring", imports = {LocalDate.class, DateTimeFormatter.class, Currency.class})
public interface IUserMapper {

    @Mapping(target = "birthday", expression = "java(mapBirthday(dto.getBirthday()))")
    @Mapping(target = "baseSalary", expression = "java(mapBaseSalary(dto.getBaseSalary()))")
    @Mapping(target = "role.id", source = "roleId")
    @Mapping(target = "typeIdentification.id", source = "typeIdentificationId")
    User toModel(CreateUserDto dto);

    default LocalDate mapBirthday(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConstants.DATE_PATTERN.getValue());
        return LocalDate.parse(birthday, formatter);
    }

    default BaseSalary mapBaseSalary(CreateBaseSalaryDto baseSalaryDto) {
        String currencyCode = baseSalaryDto.getCurrency();
        if (currencyCode == null || currencyCode.length() != 3) {
            throw new BusinessException(TechnicalMessage.INVALID_CURRENCY);
        }
        return BaseSalary.builder()
                .currency(Currency.getInstance(currencyCode))
                .value(baseSalaryDto.getValue())
                .build();
    }
}