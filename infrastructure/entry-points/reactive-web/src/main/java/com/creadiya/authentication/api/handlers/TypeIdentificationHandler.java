package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateTypeIdentificationDto;
import com.creadiya.authentication.api.mapper.ITypeIdentificationMapper;
import com.creadiya.authentication.api.validation.TypeIdentificationValidator;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class TypeIdentificationHandler {
  private  final ITypeIdentificationServicePort typeIdentificationServicePort;
  private  final ITypeIdentificationMapper mapper;
  private final TypeIdentificationValidator validator;

  public Mono<ServerResponse> createTypeIdentification(ServerRequest request) {
    return request.bodyToMono(CreateTypeIdentificationDto.class)
      .map(mapper::toModel)
      .flatMap(typeIdentification -> validator.validateName(typeIdentification).then(Mono.just(typeIdentification)))
      .flatMap(typeIdentificationServicePort::createTypeIdentification)
      .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
  }

  public Mono<ServerResponse> getAll() {
    return ServerResponse.ok().body(typeIdentificationServicePort.getAllTypeIdentifications(), Object.class);
  }
}
