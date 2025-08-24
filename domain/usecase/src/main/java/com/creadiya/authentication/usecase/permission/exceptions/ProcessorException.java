package com.creadiya.authentication.usecase.permission.exceptions;

import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;

public class ProcessorException extends RuntimeException {

  private final TechnicalMessage technicalMessage;

  public ProcessorException(Throwable cause, TechnicalMessage message) {
    super(cause);
    technicalMessage = message;
  }

  public ProcessorException(String message,
                            TechnicalMessage technicalMessage) {
    super(message);
    this.technicalMessage = technicalMessage;
  }

  public TechnicalMessage getTechnicalMessage() {
    return technicalMessage;
  }
}
