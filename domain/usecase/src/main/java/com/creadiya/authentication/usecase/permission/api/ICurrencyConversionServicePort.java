package com.creadiya.authentication.usecase.permission.api;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Currency;

public interface ICurrencyConversionServicePort {
  Mono<BigDecimal> convert(BigDecimal value, Currency currency);
}
