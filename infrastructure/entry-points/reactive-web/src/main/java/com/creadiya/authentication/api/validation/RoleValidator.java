package com.creadiya.authentication.api.validation;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.enums.IntegerConstants;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoleValidator {

  public  Mono<Void> validateName(Role role) {
    if(role.getName() == null || role.getName().isBlank() ||  role.getName().length() > IntegerConstants.MAX_ROLE_NAME_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_ROLE_NAME));
    }
    return Mono.empty();
  }

  public  Mono<Void> validateDescription(Role role) {
    if(role.getDescription() == null || role.getDescription().length() > IntegerConstants.MAX_ROLE_DESCRIPTION_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_ROLE_DESCRIPTION));
    }
    return Mono.empty();
  }
}
