package com.creadiya.authentication.api.validation;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.usecase.permission.enums.IntegerConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PermissionValidator {
  private PermissionValidator() {
  }

  public Mono<Void> validateAction(Permission permission) {
    if(permission.getResource() == null || permission.getResource().isBlank() ||  permission.getResource().length() > IntegerConstants.MAX_RESOURCE_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_PERMISSION_RESOURCE));
    }
    return Mono.empty();
  }

  public Mono<Void> validateResource(Permission permission) {
    if(permission.getAction() == null || permission.getAction().isBlank() ||  permission.getAction().length() > IntegerConstants.MAX_ACTION_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_PERMISSION_ACTION));
    }
    return Mono.empty();
  }
}
