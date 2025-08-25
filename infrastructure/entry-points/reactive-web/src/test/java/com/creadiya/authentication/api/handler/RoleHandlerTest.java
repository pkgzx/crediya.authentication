package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.handlers.RoleHandler;
import com.creadiya.authentication.api.mapper.IRoleMapper;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
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

class RoleHandlerTest {

  private IRoleServicePort roleServicePort;
  private IRoleMapper roleMapper;
  private RoleHandler roleHandler;

  @BeforeEach
  void setUp() {
    roleServicePort = Mockito.mock(IRoleServicePort.class);
    roleMapper = Mockito.mock(IRoleMapper.class);
    roleHandler = new RoleHandler(roleServicePort, roleMapper);
  }

  @Test
  void listenCreateRole_shouldReturnOkResponse() {
    CreateRoleDto dto = new CreateRoleDto();
    dto.setName("admin");
    Role role = new Role.Builder().id(1L).name("admin").description("desc").build();

    ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
    Mockito.when(serverRequest.bodyToMono(CreateRoleDto.class)).thenReturn(Mono.just(dto));
    Mockito.when(roleMapper.toModel(dto)).thenReturn(role);
    Mockito.when(roleServicePort.saveRole(role)).thenReturn(Mono.just(role));

    Mono<ServerResponse> responseMono = roleHandler.listenCreateRole(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().is2xxSuccessful() && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
      .verifyComplete();
  }

  @Test
  void listenCreateRole_shouldReturnErrorIfBodyEmpty() {
    ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
    Mockito.when(serverRequest.bodyToMono(CreateRoleDto.class)).thenReturn(Mono.empty());

    Mono<ServerResponse> responseMono = roleHandler.listenCreateRole(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().is4xxClientError())
      .verifyComplete();
  }

  @Test
  void getAllRoles_shouldReturnFluxOfRoles() {
    Role r1 = new Role.Builder().id(1L).name("admin").description("desc").build();
    Role r2 = new Role.Builder().id(2L).name("user").description("desc2").build();

    Mockito.when(roleServicePort.getAllRoles()).thenReturn(Flux.just(r1, r2));

    StepVerifier.create(roleHandler.getAllRoles())
      .expectNext(r1)
      .expectNext(r2)
      .verifyComplete();
  }
}