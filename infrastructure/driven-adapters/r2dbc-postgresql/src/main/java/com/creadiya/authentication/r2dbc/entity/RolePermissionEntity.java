package com.creadiya.authentication.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "\"RolePermission\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RolePermissionEntity {
  @Id
  private Long id;

  @Column("roleId")
  private Long roleId;

  @Column("permissionId")
  private Long permissionId;
}