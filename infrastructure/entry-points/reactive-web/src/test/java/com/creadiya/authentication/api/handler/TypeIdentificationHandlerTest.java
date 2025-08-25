package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateTypeIdentificationDto;
import com.creadiya.authentication.api.handlers.TypeIdentificationHandler;
import com.creadiya.authentication.api.mapper.ITypeIdentificationMapper;
import com.creadiya.authentication.model.typeidentification.TypeIdentification;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


class TypeIdentificationHandlerTest {

  private ITypeIdentificationServicePort servicePort;
  private ITypeIdentificationMapper mapper;
  private TypeIdentificationHandler handler;

  @BeforeEach
  void setUp() {
    servicePort = Mockito.mock(ITypeIdentificationServicePort.class);
    mapper = Mockito.mock(ITypeIdentificationMapper.class);
    handler = new TypeIdentificationHandler(servicePort, mapper);
  }

  @Test
  void createTypeIdentification_shouldReturnCreatedResponse() {
    CreateTypeIdentificationDto dto = new CreateTypeIdentificationDto();
    dto.setName("DNI");
    TypeIdentification ti = new TypeIdentification.Builder().id(1L).name("DNI").build();

    ServerRequest request = Mockito.mock(ServerRequest.class);
    Mockito.when(request.bodyToMono(CreateTypeIdentificationDto.class)).thenReturn(Mono.just(dto));
    Mockito.when(mapper.toModel(dto)).thenReturn(ti);
    Mockito.when(servicePort.createTypeIdentification(ti)).thenReturn(Mono.just(ti));

    Mono<ServerResponse> responseMono = handler.createTypeIdentification(request);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode() == HttpStatus.CREATED)
      .verifyComplete();
  }

  @Test
  void createTypeIdentification_shouldReturnBusinessError() {
    CreateTypeIdentificationDto dto = new CreateTypeIdentificationDto();
    dto.setName("DNI");
    TypeIdentification ti = new TypeIdentification.Builder().id(1L).name("DNI").build();

    ServerRequest request = Mockito.mock(ServerRequest.class);
    Mockito.when(request.bodyToMono(CreateTypeIdentificationDto.class)).thenReturn(Mono.just(dto));
    Mockito.when(mapper.toModel(dto)).thenReturn(ti);
    Mockito.when(servicePort.createTypeIdentification(ti)).thenReturn(Mono.error(new BusinessException(TechnicalMessage.TYPE_IDENTIFICATION_ALREADY_EXISTS)));

    Mono<ServerResponse> responseMono = handler.createTypeIdentification(request);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().is4xxClientError())
      .verifyComplete();
  }

  @Test
  void getAll_shouldReturnFluxOfTypeIdentifications() {
    TypeIdentification ti1 = new TypeIdentification.Builder().id(1L).name("DNI").build();
    TypeIdentification ti2 = new TypeIdentification.Builder().id(2L).name("NIE").build();

    Mockito.when(servicePort.getAllTypeIdentifications()).thenReturn(Flux.just(ti1, ti2));

    StepVerifier.create(handler.getAll())
      .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
      .verifyComplete();
  }
}