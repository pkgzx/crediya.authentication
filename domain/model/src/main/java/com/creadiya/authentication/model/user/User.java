package com.creadiya.authentication.model.user;

import java.time.LocalDate;
import java.util.UUID;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class User {
  private UUID id;
  private String name;
  private String lastName;
  private TypeIdentification typeIdentification;
  private String identification;
  private String password;
  private String email;
  private String phone;
  private String address;
  private LocalDate birthday;
  private Role role;
  private BaseSalary baseSalary;

}
