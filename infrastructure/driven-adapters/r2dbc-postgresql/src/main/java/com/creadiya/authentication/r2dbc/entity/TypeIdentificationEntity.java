package com.creadiya.authentication.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "\"TypeIdentification\"")
@AllArgsConstructor()
@NoArgsConstructor
@Data
@Builder
public class TypeIdentificationEntity {
  @Id
  private Long id;

  private String name;
}
