package com.creadiya.authentication.api.client;

import com.creadiya.authentication.usecase.permission.api.ICurrencyConversionServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Slf4j
@Component
public class CurrencyConversionServicePortCdnClient implements ICurrencyConversionServicePort {

  private final WebClient webClient;
  private final String apiUrl;

  public CurrencyConversionServicePortCdnClient(WebClient.Builder webClientBuilder,
                                                @Value("${currency.api.base-url}") String apiUrl) {
    this.webClient = webClientBuilder.build();
    this.apiUrl = apiUrl;
  }

  @Override
  public Mono<BigDecimal> convert(BigDecimal value, Currency currency) {
    return webClient.get()
      .uri(apiUrl)
      .retrieve()
      .bodyToMono(CurrencyApiResponse.class)
      .map(response -> {
        BigDecimal rate = response.getRateForCurrency(currency.getCurrencyCode().toLowerCase());
        return value.divide(rate, 10, RoundingMode.valueOf(RoundingMode.HALF_UP.ordinal()));
      });
  }


  public static class CurrencyApiResponse {
    private java.util.Map<String, BigDecimal> cop;

    public BigDecimal getRateForCurrency(String currency) {
      return cop.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void setCop(java.util.Map<String, BigDecimal> cop) {
      this.cop = cop;
    }
  }
}