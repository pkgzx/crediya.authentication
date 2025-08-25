package com.creadiya.authentication.usecase.permission.validation;

import com.creadiya.authentication.model.typeidentification.TypeIdentification;
import com.creadiya.authentication.usecase.permission.enums.IntegerConstants;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import reactor.core.publisher.Mono;

public class TypeIdentificationValidator {
    private TypeIdentificationValidator() {
    }

    public static Mono<Void> validateName(TypeIdentification typeIdentification) {
      if(typeIdentification.getName() == null || typeIdentification.getName().isBlank() ||  typeIdentification.getName().length() > IntegerConstants.MAX_TYPE_IDENTIFICATION_NAME_LENGTH.value) {
        return Mono.error(new BusinessException(TechnicalMessage.INVALID_TYPE_IDENTIFICATION_NAME));
      }
      return Mono.empty();
    }

}
