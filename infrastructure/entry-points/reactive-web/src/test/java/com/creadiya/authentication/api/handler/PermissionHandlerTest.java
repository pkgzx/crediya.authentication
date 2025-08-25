package com.creadiya.authentication.api.handler;


import com.creadiya.authentication.api.dto.CreatePermissionDto;
import com.creadiya.authentication.api.handlers.PermissionHandler;
import com.creadiya.authentication.api.mapper.IPermissionMapper;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;


class PermissionHandlerTest {

  private IPermissionServicePort permissionServicePort;
  private IPermissionMapper permissionMapper;
  private PermissionHandler permissionHandler;

  @BeforeEach
  void setUp() {
    permissionServicePort = Mockito.mock(IPermissionServicePort.class);
    permissionMapper = Mockito.mock(IPermissionMapper.class);
    permissionHandler = new PermissionHandler(permissionServicePort, permissionMapper);
  }

  @Test
  void listenCreatePermission_shouldReturnOkResponse() {
    CreatePermissionDto dto = new CreatePermissionDto();
    dto.setResource("res");
    dto.setAction("act");
    Permission permission = new Permission.Builder().id(1L).resource("res").action("act").build();

    ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
    Mockito.when(serverRequest.bodyToMono(CreatePermissionDto.class)).thenReturn(Mono.just(dto));
    Mockito.when(permissionMapper.toModel(dto)).thenReturn(permission);
    Mockito.when(permissionServicePort.savePermission(permission)).thenReturn(Mono.just(permission));

    Mono<ServerResponse> responseMono = permissionHandler.listenCreatePermission(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().is2xxSuccessful() && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
      .verifyComplete();
  }

  @Test
  void getAllPermissions_shouldReturnFluxOfPermissions() {
    Permission p1 = new Permission.Builder().id(1L).resource("r1").action("a1").build();
    Permission p2 = new Permission.Builder().id(2L).resource("r2").action("a2").build();

    Mockito.when(permissionServicePort.getAllPermissions()).thenReturn(Flux.just(p1, p2));

    Mono<ServerResponse> responseMono = permissionHandler.getAllPermissions();

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
      .verifyComplete();
  }
}