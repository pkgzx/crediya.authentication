package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateTypeIdentificationDto;
import com.creadiya.authentication.api.dto.ErrorDto;
import com.creadiya.authentication.api.mapper.ITypeIdentificationMapper;
import com.creadiya.authentication.api.util.ErrorBuilder;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TypeIdentificationHandler {
  private  final ITypeIdentificationServicePort typeIdentificationServicePort;
  private  final ITypeIdentificationMapper mapper;

  public Mono<ServerResponse> createTypeIdentification(ServerRequest request) {
    return request.bodyToMono(CreateTypeIdentificationDto.class)
      .map(mapper::toModel)
      .flatMap(typeIdentificationServicePort::createTypeIdentification)
      .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response))
      .onErrorResume(BusinessException.class,
        ex -> ErrorBuilder.<Role>buildErrorResponse(HttpStatus.resolve(ex.getTechnicalMessage().getCode()),
          List.of(ErrorDto.builder().message(ex.getMessage()).build())))
      .onErrorResume(ex -> {
        log.error("Unexpected error occurred", ex);
        return ErrorBuilder.<Role>buildErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          List.of(ErrorDto.builder()
            .code(TechnicalMessage.INTERNAL_ERROR.getCode())
            .message(TechnicalMessage.INTERNAL_ERROR.getMessage())
            .build()));
      });
  }

  public Mono<ServerResponse> getAll() {
    return ServerResponse.ok().body(typeIdentificationServicePort.getAllTypeIdentifications(), Object.class);
  }
}
