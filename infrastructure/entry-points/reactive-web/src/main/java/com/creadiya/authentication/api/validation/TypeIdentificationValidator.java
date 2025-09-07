package com.creadiya.authentication.api.validation;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.enums.IntegerConstants;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TypeIdentificationValidator {


    public  Mono<Void> validateName(TypeIdentification typeIdentification) {
      if(typeIdentification.getName() == null || typeIdentification.getName().isBlank() ||  typeIdentification.getName().length() > IntegerConstants.MAX_TYPE_IDENTIFICATION_NAME_LENGTH.value) {
        return Mono.error(new BusinessException(TechnicalMessage.INVALID_TYPE_IDENTIFICATION_NAME));
      }
      return Mono.empty();
    }

}
