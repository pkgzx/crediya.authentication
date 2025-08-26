package com.creadiya.authentication.model.user;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class BaseSalary {
    private Currency currency;
    private BigDecimal value;
}
