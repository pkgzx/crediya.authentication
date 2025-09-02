package com.creadiya.authentication.model.permission;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Permission {
  private Long id;
  private String resource; // user
  private String action;  // create
  // path : /api/v1/users
  //   // method: post
}
