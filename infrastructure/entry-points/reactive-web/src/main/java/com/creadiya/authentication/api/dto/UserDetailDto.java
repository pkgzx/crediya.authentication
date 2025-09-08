package com.creadiya.authentication.api.dto;

import java.math.BigDecimal;

public record UserDetailDto (
  String id,
  String name,
  String lastName,
  String identification,
  String email,
  BigDecimal baseSalary
) {
}