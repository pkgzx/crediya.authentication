package com.creadiya.authentication.api.validation;

import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.enums.IntegerConstants;
import com.creadiya.authentication.model.enums.StringConstants;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class UserValidator {

  public  Mono<Void> validEmail(String email) {
    String emailRegex = StringConstants.EMAIL_PATTERN.getValue();
    if(!(email != null && email.matches(emailRegex) && email.length() >= IntegerConstants.MIN_EMAIL_LENGTH.value && email.length() <= IntegerConstants.MAX_EMAIL_LENGTH.value)) {
      return Mono.error(new BusinessException(TechnicalMessage.EMAIL_FORMAT_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validPassword(String password) {
    String passwordRegex = StringConstants.PASSWORD_PATTERN.getValue();
    if( password == null  || password.matches(passwordRegex) || password.length() < IntegerConstants.MIN_PASSWORD_LENGTH.value || password.length() > IntegerConstants.MAX_PASSWORD_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.PASSWORD_FORMAT_INVALID));
    }
    return Mono.empty();
  }





  public  Mono<Void> validPhone(String phone) {
    if(!(phone != null && phone.length() >= IntegerConstants.MIN_PHONE_LENGTH.value && phone.length() <= IntegerConstants.MAX_PHONE_LENGTH.value && phone.matches(StringConstants.PHONE_PATTERN.getValue()))){
      return Mono.error(new BusinessException(TechnicalMessage.PHONE_FORMAT_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validName(String name) {
    if(!(name != null && name.length() >= IntegerConstants.MIN_NAME_LENGTH.value && name.length() <= IntegerConstants.MAX_NAME_LENGTH.value)){
      return Mono.error(new BusinessException(TechnicalMessage.NAME_FORMAT_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validLastName(String lastName) {
    if(!(lastName != null && lastName.length() >= IntegerConstants.MIN_NAME_LENGTH.value && lastName.length() <= IntegerConstants.MAX_NAME_LENGTH.value)){
      return Mono.error(new BusinessException(TechnicalMessage.LAST_NAME_FORMAT_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validIdentification(String identification) {
    if (Objects.isNull(identification) ||
      identification.length() < IntegerConstants.MIN_IDENTIFICATION_LENGTH.value ||
      identification.length() > IntegerConstants.MAX_IDENTIFICATION_LENGTH.value ||
      !identification.matches(StringConstants.IDENTIFICATION_PATTERN.getValue())) {
      return Mono.error(new BusinessException(TechnicalMessage.IDENTIFICATION_FORMAT_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validAddress(String address) {
    if(address != null && address.length() > IntegerConstants.MAX_ADDRESS_LENGTH.value){
      return Mono.error(new BusinessException(TechnicalMessage.ADDRESS_FORMAT_INVALID));
    }
    return Mono.empty();
  }



  public  Mono<Void> validateCurrency(String currency) {
    try {
      java.util.Currency.getInstance(currency);
    } catch (IllegalArgumentException e) {
      return Mono.error(new BusinessException(TechnicalMessage.BASE_SALARY_CURRENCY_INVALID));
    }
    return Mono.empty();
  }

  public  Mono<Void> validOtherEntities(User user){
    if (user.getRole() == null || user.getRole().getId() == null) {
      return Mono.error(new BusinessException(TechnicalMessage.ROLE_NOT_FOUND));
    }
    if (user.getTypeIdentification() == null || user.getTypeIdentification().getId() == null) {
      return Mono.error(new BusinessException(TechnicalMessage.TYPE_IDENTIFICATION_NOT_FOUND));
    }
    return Mono.empty();
  }


}
