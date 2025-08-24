package com.creadiya.authentication.api.util;

import com.creadiya.authentication.api.dto.ErrorDto;
import com.creadiya.authentication.api.dto.ResponseApiDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

public class ErrorBuilder {
  private ErrorBuilder() {
  }
  public static <T>  Mono<ServerResponse> buildErrorResponse(HttpStatus httpStatus, List<ErrorDto> errors) {
    return Mono.defer(() -> {
      ResponseApiDto<T> apiErrorResponse = ResponseApiDto.<T>builder()
        .code(httpStatus.value())
        .date(Instant.now().toString())
        .errors(errors)
        .build();
      return ServerResponse.status(httpStatus).bodyValue(apiErrorResponse);
    });
  }
}
