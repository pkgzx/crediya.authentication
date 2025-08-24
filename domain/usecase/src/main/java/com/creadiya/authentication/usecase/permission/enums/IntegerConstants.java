package com.creadiya.authentication.usecase.permission.enums;

public enum IntegerConstants {
  MAX_RESOURCE_LENGTH(50),
  MAX_ACTION_LENGTH(50);

  public final int value;

  IntegerConstants(int value) {
    this.value = value;
  }
}
