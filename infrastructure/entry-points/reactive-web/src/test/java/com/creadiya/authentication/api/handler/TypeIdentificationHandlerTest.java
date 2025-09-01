package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateTypeIdentificationDto;
import com.creadiya.authentication.api.handlers.TypeIdentificationHandler;
import com.creadiya.authentication.api.mapper.ITypeIdentificationMapper;
import com.creadiya.authentication.api.validation.TypeIdentificationValidator;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

class TypeIdentificationHandlerTest {

    private ITypeIdentificationServicePort servicePort;
    private ITypeIdentificationMapper mapper;
    private TypeIdentificationValidator validator;
    private TypeIdentificationHandler handler;

    @BeforeEach
    void setUp() {
        servicePort = mock(ITypeIdentificationServicePort.class);
        mapper = mock(ITypeIdentificationMapper.class);
        validator = mock(TypeIdentificationValidator.class);
        handler = new TypeIdentificationHandler(servicePort, mapper, validator);
    }

    @Test
    void createTypeIdentification_shouldReturnCreatedResponse() {
        CreateTypeIdentificationDto dto = new CreateTypeIdentificationDto();
        TypeIdentification typeIdentification = new TypeIdentification();

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(CreateTypeIdentificationDto.class)).thenReturn(Mono.just(dto));
        when(mapper.toModel(dto)).thenReturn(typeIdentification);
        when(validator.validateName(typeIdentification)).thenReturn(Mono.empty());
        when(servicePort.createTypeIdentification(typeIdentification)).thenReturn(Mono.just(typeIdentification));

        Mono<ServerResponse> responseMono = handler.createTypeIdentification(request);

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
            .verifyComplete();
    }

    @Test
    void getAll_shouldReturnOkResponse() {
        TypeIdentification t1 = new TypeIdentification();
        TypeIdentification t2 = new TypeIdentification();
        when(servicePort.getAllTypeIdentifications()).thenReturn(Flux.just(t1, t2));

        Mono<ServerResponse> responseMono = handler.getAll();

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
            .verifyComplete();
    }
}