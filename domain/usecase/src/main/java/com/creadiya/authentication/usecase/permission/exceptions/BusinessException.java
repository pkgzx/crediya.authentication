package com.creadiya.authentication.usecase.permission.exceptions;

import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;

public class BusinessException extends ProcessorException {

  private final String paramValue;

  public BusinessException(TechnicalMessage technicalMessage, String paramValue) {
    super(technicalMessage.getMessage(), technicalMessage);
    this.paramValue = paramValue;
  }

  public BusinessException(TechnicalMessage technicalMessage) {
    this(technicalMessage, technicalMessage.getParam());
  }

  public String getParamValue() {
    return paramValue;
  }
}