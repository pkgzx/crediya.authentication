package com.creadiya.authentication.model.enums;

import java.math.BigDecimal;

public enum MoneyConstants {
    MIN_COP_BASE_SALARY(BigDecimal.valueOf(0)),
    MAX_COP_BASE_SALARY(BigDecimal.valueOf(15000000)); // 15,000,000 COP

    private final BigDecimal value;

    MoneyConstants(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}