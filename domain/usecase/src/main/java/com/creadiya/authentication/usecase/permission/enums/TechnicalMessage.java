package com.creadiya.authentication.usecase.permission.enums;

public enum TechnicalMessage {

  INTERNAL_ERROR(500,"Something went wrong, please try again", ""),
  INVALID_PERMISSION_RESOURCE(400, "Invalid resource. Must not be empty and max 50 chars.", "resource"),
  INVALID_PERMISSION_ACTION(400, "Invalid action. Must not be empty and max 50 chars.", "action"),
  PERMISSION_ALREADY_EXISTS(409, "Permission already exists.", "resource, action"),
  REQUEST_BODY_EMPTY(400, "Body can't be empty.", ""),
  INVALID_ROLE_NAME(400, "Invalid name. Must not be empty and max 50 chars.", "name"),
  INVALID_ROLE_DESCRIPTION(400, "Invalid description. Max 255 chars.", "description"),
  PERMISSION_NOT_FOUND(404, "Permission not found.", "id"),
  ROLE_ALREADY_EXISTS(409, "Role already exists.", "name")
  ;


  private final Integer code;
  private final String message;
  private final String param;

  TechnicalMessage(Integer code, String message, String param) {
    this.code = code;
    this.message = message;
    this.param = param;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getParam() {
    return param;
  }
}