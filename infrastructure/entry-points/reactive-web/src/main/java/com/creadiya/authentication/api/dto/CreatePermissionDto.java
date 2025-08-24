package com.creadiya.authentication.api.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreatePermissionDto {
  private String resource;
  private String action;
}
