package com.creadiya.authentication.usecase.permission.validation;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.enums.IntegerConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class RoleValidator {
  private RoleValidator() {
  }

  public static Mono<Void> validateName(Role role) {
    if(role.getName() == null || role.getName().isBlank() ||  role.getName().length() > IntegerConstants.MAX_ROLE_NAME_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_ROLE_NAME));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateDescription(Role role) {
    if(role.getDescription() == null || role.getDescription().length() > IntegerConstants.MAX_ROLE_DESCRIPTION_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_ROLE_DESCRIPTION));
    }
    return Mono.empty();
  }
}
