package com.creadiya.authentication.api.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateBaseSalaryDto {
    private String currency;
    private BigDecimal value;
}
