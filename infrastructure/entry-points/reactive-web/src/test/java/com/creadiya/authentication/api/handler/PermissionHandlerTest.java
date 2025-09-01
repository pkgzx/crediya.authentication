package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreatePermissionDto;
import com.creadiya.authentication.api.handlers.PermissionHandler;
import com.creadiya.authentication.api.mapper.IPermissionMapper;
import com.creadiya.authentication.api.validation.PermissionValidator;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.Mockito.*;

class PermissionHandlerTest {

    private IPermissionServicePort permissionServicePort;
    private IPermissionMapper permissionMapper;
    private PermissionValidator permissionValidator;
    private PermissionHandler permissionHandler;

    @BeforeEach
    void setUp() {
        permissionServicePort = mock(IPermissionServicePort.class);
        permissionMapper = mock(IPermissionMapper.class);
        permissionValidator = mock(PermissionValidator.class);
        permissionHandler = new PermissionHandler(permissionServicePort, permissionMapper, permissionValidator);
    }

    @Test
    void listenCreatePermission_shouldReturnOkResponse() {
        CreatePermissionDto dto = new CreatePermissionDto();
        Permission permission = new Permission();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(CreatePermissionDto.class)).thenReturn(Mono.just(dto));
        when(permissionMapper.toModel(dto)).thenReturn(permission);
        when(permissionValidator.validateResource(permission)).thenReturn(Mono.empty());
        when(permissionValidator.validateAction(permission)).thenReturn(Mono.empty());
        when(permissionServicePort.savePermission(permission)).thenReturn(Mono.just(permission));

        Mono<ServerResponse> responseMono = permissionHandler.listenCreatePermission(serverRequest);

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(org.springframework.http.HttpStatus.OK)
                && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
            .verifyComplete();
    }

    @Test
    void getAllPermissions_shouldReturnOkResponse() {
        Permission permission1 = new Permission();
        Permission permission2 = new Permission();
        when(permissionServicePort.getAllPermissions()).thenReturn(Flux.just(permission1, permission2));

        Mono<ServerResponse> responseMono = permissionHandler.getAllPermissions();

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(org.springframework.http.HttpStatus.OK))
            .verifyComplete();
    }
}