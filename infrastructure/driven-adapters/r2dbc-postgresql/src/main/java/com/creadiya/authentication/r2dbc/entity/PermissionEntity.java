package com.creadiya.authentication.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table( name = "\"Permission\"")
@AllArgsConstructor()
@NoArgsConstructor
@Data
@Builder
public class PermissionEntity {
    @Id
    private Long id;

    private String resource;

    private String action;
}
