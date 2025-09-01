
package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.handlers.RoleHandler;
import com.creadiya.authentication.api.mapper.IRoleMapper;
import com.creadiya.authentication.api.validation.RoleValidator;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
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

class RoleHandlerTest {

    private IRoleServicePort roleServicePort;
    private IRoleMapper roleMapper;
    private RoleValidator roleValidator;
    private RoleHandler roleHandler;

    @BeforeEach
    void setUp() {
        roleServicePort = mock(IRoleServicePort.class);
        roleMapper = mock(IRoleMapper.class);
        roleValidator = mock(RoleValidator.class);
        roleHandler = new RoleHandler(roleServicePort, roleMapper, roleValidator);
    }

    @Test
    void listenCreateRole_shouldReturnOkResponse() {
        CreateRoleDto dto = new CreateRoleDto();
        Role role = new Role();

        ServerRequest serverRequest = mock(ServerRequest.class);
        when(serverRequest.bodyToMono(CreateRoleDto.class)).thenReturn(Mono.just(dto));
        when(roleMapper.toModel(dto)).thenReturn(role);
        when(roleValidator.validateName(role)).thenReturn(Mono.empty());
        when(roleValidator.validateDescription(role)).thenReturn(Mono.empty());
        when(roleServicePort.saveRole(role)).thenReturn(Mono.just(role));

        Mono<ServerResponse> responseMono = roleHandler.listenCreateRole(serverRequest);

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().is2xxSuccessful()
                && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
            .verifyComplete();
    }

    @Test
    void getAllRoles_shouldReturnOkResponse() {
        Role role1 = new Role();
        Role role2 = new Role();
        when(roleServicePort.getAllRoles()).thenReturn(Flux.just(role1, role2));

        Mono<ServerResponse> responseMono = roleHandler.getAllRoles();

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().is2xxSuccessful()
                && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
            .verifyComplete();
    }

    @Test
    void getAllRoles_shouldReturnNoContentResponse() {
        when(roleServicePort.getAllRoles()).thenReturn(Flux.empty());

        Mono<ServerResponse> responseMono = roleHandler.getAllRoles();

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().is2xxSuccessful()
                && response.statusCode().value() == 204)
            .verifyComplete();
    }
}