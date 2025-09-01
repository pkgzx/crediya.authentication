package com.creadiya.authentication.api.rest;

import com.creadiya.authentication.api.RouterRest;
import com.creadiya.authentication.api.config.path.PermissionPath;
import com.creadiya.authentication.api.config.path.RolePath;
import com.creadiya.authentication.api.config.path.TypeIdentificationPath;
import com.creadiya.authentication.api.config.path.UserPath;
import com.creadiya.authentication.api.exception.GlobalWebExceptionHandler;
import com.creadiya.authentication.api.handlers.PermissionHandler;
import com.creadiya.authentication.api.handlers.RoleHandler;
import com.creadiya.authentication.api.handlers.TypeIdentificationHandler;
import com.creadiya.authentication.api.handlers.UserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;

import static org.mockito.Mockito.lenient;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RouterRestTest {

    private WebTestClient webTestClient;


@BeforeEach
void setUp() {
    PermissionPath permissionPath = Mockito.mock(PermissionPath.class);
    RolePath rolePath = Mockito.mock(RolePath.class);
    TypeIdentificationPath typeIdentificationPath = Mockito.mock(TypeIdentificationPath.class);
    UserPath userPath = Mockito.mock(UserPath.class);

    when(permissionPath.getCreate()).thenReturn("/api/v1/permissions");
    when(permissionPath.getList()).thenReturn("/api/v1/permissions");
    when(rolePath.getCreate()).thenReturn("/api/v1/roles");
    when(rolePath.getList()).thenReturn("/api/v1/roles");
    when(typeIdentificationPath.getCreate()).thenReturn("/api/v1/type-identifications");
    when(typeIdentificationPath.getList()).thenReturn("/api/v1/type-identifications");
    when(userPath.getCreate()).thenReturn("/api/v1/users");
    when(userPath.getGetByEmail()).thenReturn("/api/v1/users/email/{email}");

    PermissionHandler permissionHandler = Mockito.mock(PermissionHandler.class);
    RoleHandler roleHandler = Mockito.mock(RoleHandler.class);
    TypeIdentificationHandler typeIdentificationHandler = Mockito.mock(TypeIdentificationHandler.class);
    UserHandler userHandler = Mockito.mock(UserHandler.class);

    // Configurar mocks de handlers
    lenient().when(permissionHandler.listenCreatePermission(any(ServerRequest.class)))
            .thenReturn(ServerResponse.ok().build());
    lenient().when(roleHandler.listenCreateRole(any(ServerRequest.class)))
            .thenReturn(ServerResponse.ok().build());
    lenient().when(typeIdentificationHandler.createTypeIdentification(any(ServerRequest.class)))
            .thenReturn(ServerResponse.ok().build());
    lenient().when(userHandler.createUser(any(ServerRequest.class)))
            .thenReturn(ServerResponse.ok().build());
    lenient().when(userHandler.getUserByEmail(any(ServerRequest.class)))
            .thenReturn(ServerResponse.ok().build());

    lenient().when(permissionHandler.getAllPermissions())
            .thenReturn(ServerResponse.ok().build());
    lenient().when(roleHandler.getAllRoles())
            .thenReturn(ServerResponse.ok().build());
    lenient().when(typeIdentificationHandler.getAll())
            .thenReturn(ServerResponse.ok().build());

    // Crear una instancia REAL del GlobalWebExceptionHandler en lugar de mock
    GlobalWebExceptionHandler globalWebExceptionHandler = new GlobalWebExceptionHandler();

    RouterRest routerRest = new RouterRest(permissionPath, rolePath, typeIdentificationPath, userPath);

    RouterFunction<ServerResponse> routerFunction = routerRest.routerFunction(
            permissionHandler, roleHandler, typeIdentificationHandler, userHandler, globalWebExceptionHandler);

    webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
}
    @Test
    void testCreatePermissionRoute() {
        webTestClient.post()
                .uri("/api/v1/permissions")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetAllPermissionsRoute() {
        webTestClient.get()
                .uri("/api/v1/permissions")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateRoleRoute() {
        webTestClient.post()
                .uri("/api/v1/roles")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetAllRolesRoute() {
        webTestClient.get()
                .uri("/api/v1/roles")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateTypeIdentificationRoute() {
        webTestClient.post()
                .uri("/api/v1/type-identifications")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetAllTypeIdentificationsRoute() {
        webTestClient.get()
                .uri("/api/v1/type-identifications")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateUserRoute() {
        webTestClient.post()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetUserByEmailRoute() {
        webTestClient.get()
                .uri("/api/v1/users/email/test@email.com")
                .exchange()
                .expectStatus().isOk();
    }
}