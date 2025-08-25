package com.creadiya.authentication.api.dto;


import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CreateRoleDto {
  private String name;
  private String description;
  private List<Integer> permissionIds;
}
