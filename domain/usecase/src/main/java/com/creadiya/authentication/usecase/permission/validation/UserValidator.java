package com.creadiya.authentication.usecase.permission.validation;

import com.creadiya.authentication.usecase.permission.enums.IntegerConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserValidator {
  private UserValidator() {
  }


  public static Mono<Void> validateMinimumAge(LocalDate birthday) {
      LocalDate today = LocalDate.now();
      LocalDate adultDate = today.minusYears(IntegerConstants.MINIMUM_AGE.value);
      if(birthday.isAfter(adultDate)) {
        return Mono.error(new BusinessException(TechnicalMessage.INVALID_MINIMUN_LEGAL_AGE));
      }
    return Mono.empty();
  }



  public static Mono<Void> validateBaseSalaryValue(BigDecimal value) {
    if(value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
      return Mono.error(new BusinessException(TechnicalMessage.BASE_SALARY_VALUE_INVALID));
    }
    return Mono.empty();
  }

}
