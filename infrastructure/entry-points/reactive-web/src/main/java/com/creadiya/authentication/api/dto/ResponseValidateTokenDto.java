package com.creadiya.authentication.api.dto;

public record ResponseValidateTokenDto(
  String identification,
  String email,
  String role
) {
}
