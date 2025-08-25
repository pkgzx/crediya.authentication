package com.creadiya.authentication.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table( name = "\"IUserTypeIdentification\"")
@AllArgsConstructor()
@NoArgsConstructor
@Data
@Builder
public class UserTypeIdentificationEntity {
  private Long id;

  private Long userId;

  private Long typeIdentificationId;

  private String identificationNumber;
}
