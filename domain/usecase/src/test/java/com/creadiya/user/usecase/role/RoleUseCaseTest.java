package com.creadiya.user.usecase.role;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import com.creadiya.authentication.usecase.permission.cases.RoleUseCase;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

class RoleUseCaseTest {

  private IRoleRepository roleRepository;
  private IPermissionServicePort permissionServicePort;
  private RoleUseCase roleUseCase;

  @BeforeEach
  void setUp() {
    roleRepository = Mockito.mock(IRoleRepository.class);
    permissionServicePort = Mockito.mock(IPermissionServicePort.class);
    roleUseCase = new RoleUseCase(roleRepository, permissionServicePort);
  }

  @Test
  void saveRole_shouldReturnSavedRole() {
    Permission p1 = new Permission.Builder().id(1L).resource("res").action("act").build();
    Role role = new Role.Builder().id(null).name("admin").description("desc").permissions(Arrays.asList(p1)).build();

    Mockito.when(roleRepository.findByName("admin")).thenReturn(Mono.just(false));
    Mockito.when(permissionServicePort.getPermissionById(1L)).thenReturn(Mono.just(p1));
    Mockito.when(roleRepository.save(role)).thenReturn(Mono.just(role));

    StepVerifier.create(roleUseCase.saveRole(role))
      .expectNext(role)
      .verifyComplete();
  }

  @Test
  void saveRole_shouldThrowIfRoleExists() {
    Role role = new Role.Builder().id(null).name("admin").description("desc").build();

    Mockito.when(roleRepository.findByName("admin")).thenReturn(Mono.just(true));
    Mockito.when(roleRepository.save(role)).thenReturn(Mono.just(role)); // <-- Añadir este mock

    StepVerifier.create(roleUseCase.saveRole(role))
      .expectErrorMatches(e -> e instanceof BusinessException &&
        ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.ROLE_ALREADY_EXISTS)
      .verify();
  }

  @Test
  void saveRole_shouldThrowIfPermissionNotFound() {
    Permission p1 = new Permission.Builder().id(1L).resource("res").action("act").build();
    Role role = new Role.Builder().id(null).name("admin").description("desc").permissions(Arrays.asList(p1)).build();

    Mockito.when(roleRepository.findByName("admin")).thenReturn(Mono.just(false));
    Mockito.when(permissionServicePort.getPermissionById(1L)).thenReturn(Mono.empty());
    Mockito.when(roleRepository.save(role)).thenReturn(Mono.just(role)); // <-- Añadir este mock

    StepVerifier.create(roleUseCase.saveRole(role))
      .expectErrorMatches(e -> e instanceof BusinessException &&
        ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.PERMISSION_NOT_FOUND)
      .verify();
  }

  @Test
  void getAllRoles_shouldReturnAllRoles() {
    Role r1 = new Role.Builder().id(1L).name("admin").description("desc").build();
    Role r2 = new Role.Builder().id(2L).name("user").description("desc2").build();

    Mockito.when(roleRepository.findAll()).thenReturn(Flux.just(r1, r2));

    StepVerifier.create(roleUseCase.getAllRoles())
      .expectNext(r1)
      .expectNext(r2)
      .verifyComplete();
  }
}