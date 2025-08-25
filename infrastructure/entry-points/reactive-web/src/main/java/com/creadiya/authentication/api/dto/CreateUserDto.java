package com.creadiya.authentication.api.dto;

import lombok.*;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
  private String name;
  private String lastName;
  private String identification;
  private String password;
  private String email;
  private String phone;
  private String address;
  private String birthday;
  private Long roleId;
  private CreateBaseSalaryDto baseSalary;
  private Long typeIdentificationId;
}


