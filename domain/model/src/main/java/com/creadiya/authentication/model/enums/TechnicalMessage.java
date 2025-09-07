package com.creadiya.authentication.model.enums;

public enum TechnicalMessage {

  INTERNAL_ERROR(500,"Something went wrong, please try again", ""),
  INVALID_PERMISSION_RESOURCE(400, "Invalid resource. Must not be empty and max 50 chars.", "resource"),
  INVALID_PERMISSION_ACTION(400, "Invalid action. Must not be empty and max 50 chars.", "action"),
  PERMISSION_ALREADY_EXISTS(409, "Permission already exists.", "resource, action"),
  REQUEST_BODY_EMPTY(400, "Body can't be empty.", ""),
  INVALID_ROLE_NAME(400, "Invalid name. Must not be empty and max 50 chars.", "name"),
  INVALID_ROLE_DESCRIPTION(400, "Invalid description. Max 255 chars.", "description"),
  PERMISSION_NOT_FOUND(404, "Permission not found.", "id"),
  ROLE_ALREADY_EXISTS(409, "Role already exists.", "name"),
  ROLE_NOT_FOUND(404, "Role not found.", "id"),
  INVALID_PARAM(400, "Invalid param", "param"),
  TYPE_IDENTIFICATION_ALREADY_EXISTS(409, "Type Identification already exists.", "name"),
  INVALID_TYPE_IDENTIFICATION_NAME(400, "Invalid name. Must not be empty and max 50 chars.", "name"),
  DATE_FORMAT_INVALID(400, "Invalid date format. Expected format: " + StringConstants.DATE_PATTERN.getValue(),
    "birthday"),
  EMAIL_FORMAT_INVALID(400, "Invalid email format, must not be empty and min 5 chars and max 250 chars.", "email"),
  PASSWORD_FORMAT_INVALID(400, "Invalid password format. Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.", "password"),
  PHONE_FORMAT_INVALID(400, "Invalid phone format. Must not be empty and max 15 chars.", "phone"),
  NAME_FORMAT_INVALID(400, "Invalid name format. Must not be empty and min, 2 max 50 chars.", "name"),
  LAST_NAME_FORMAT_INVALID(400, "Invalid last name format. Must not be empty and min 2, max 50 chars.", "lastName"),
  IDENTIFICATION_FORMAT_INVALID(400, "Invalid identification format. Must not be empty and max 20 chars.", "identification"),
  ADDRESS_FORMAT_INVALID(400, "Invalid address format. Max 100 chars.", "address"),
  ROLE_ID_NOT_FOUND(404, "Role ID not found.", "roleId"),
  TYPE_IDENTIFICATION_ID_NOT_FOUND(404, "Type Identification ID not found.", "typeIdentificationId"),
  BASE_SALARY_CURRENCY_INVALID(400, "Invalid base salary currency. Must be a valid ISO 4217 currency code.", "baseSalary.currency"),
  USER_BASE_SALARY_OUT_OF_RANGE(400, "User base salary is out of the acceptable range.", "baseSalary.value"),
  BASE_SALARY_VALUE_INVALID(400, "Invalid base salary value. Must be greater than 0.", "baseSalary.value"),
  USER_ALREADY_EXISTS(409, "User already exists.", "email, identification"),
  INVALID_MINIMUN_LEGAL_AGE(400, "User must be at least 18 years old.", "birthday"),
  INVALID_CURRENCY(400, "Invalid currency. Must be a valid ISO 4217 currency code.", "baseSalary.currency"),
  USER_EMAIL_ALREADY_EXISTS(409, "Email already exists.", "email"),
  USER_IDENTIFICATION_ALREADY_EXISTS(409, "Identification already exists.", "identification"),
  USER_PHONE_ALREADY_EXISTS(409, "Phone already exists.", "phone"),
  TYPE_IDENTIFICATION_NOT_FOUND(404, "Type Identification not found.", "id"),
  REQUEST_BODY_INVALID(400, "Request body is invalid.", "body"),
  INVALID_CREDENTIALS(401, "Invalid credentials.", "credentials"),
  INVALID_AUTHENTICATION(401, "Invalid authentication.", "authentication"),
  INVALID_TOKEN(401, "Invalid token.", "token"),
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