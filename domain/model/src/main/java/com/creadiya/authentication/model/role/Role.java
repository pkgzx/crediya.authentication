package com.creadiya.authentication.model.role;

import lombok.*;

import java.util.List;


import com.creadiya.authentication.model.permission.Permission;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Role {
  private Long id;
  private String name;
  private String description;
  private List<Permission> permissions;
}
