package com.creadiya.authentication.usecase.permission.validation;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.usecase.permission.enums.IntegerConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class PermissionValidator {
  private PermissionValidator() {
  }

  public static Mono<Void> validateAction(Permission permission) {
    if(permission.getResource() == null || permission.getResource().isBlank() ||  permission.getResource().length() > IntegerConstants.MAX_RESOURCE_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_PERMISSION_RESOURCE));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateResource(Permission permission) {
    if(permission.getAction() == null || permission.getAction().isBlank() ||  permission.getAction().length() > IntegerConstants.MAX_ACTION_LENGTH.value) {
      return Mono.error(new BusinessException(TechnicalMessage.INVALID_PERMISSION_ACTION));
    }
    return Mono.empty();
  }
}
