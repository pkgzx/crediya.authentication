package com.creadiya.authentication.r2dbc.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table( name = "\"User\"")
@AllArgsConstructor()
@NoArgsConstructor
@Data
@Builder
public class UserEntity {
  private String id;
  private String name;
  @Column("lastname")
  private String lastName;
  private String identification;
  private String password;
  private String email;
  private String phone;
  private String address;
  private LocalDate birthday;
  @Column("roleid")
  private Long roleId;
  @Column("basesalarycurrency")
  private String baseSalaryCurrency;
  @Column("basesalaryvalue")
  private BigDecimal baseSalaryValue;
  @Column("typeidentificationid")
  private Long typeIdentificationId;
}
