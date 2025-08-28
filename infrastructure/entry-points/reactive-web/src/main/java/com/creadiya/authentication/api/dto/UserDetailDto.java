package com.creadiya.authentication.api.dto;

public record UserDetailDto (
  String id,
  String name,
  String lastName,
  String identification,
  String email
) {
}