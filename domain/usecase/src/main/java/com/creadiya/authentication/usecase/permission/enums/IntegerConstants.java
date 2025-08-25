package com.creadiya.authentication.usecase.permission.enums;

public enum IntegerConstants {
  MAX_RESOURCE_LENGTH(50),
  MAX_ACTION_LENGTH(50),
  MAX_ROLE_NAME_LENGTH(30),
  MAX_ROLE_DESCRIPTION_LENGTH(256);

  public final int value;

  IntegerConstants(int value) {
    this.value = value;
  }
}
