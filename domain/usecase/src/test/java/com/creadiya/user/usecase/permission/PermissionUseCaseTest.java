package com.creadiya.user.usecase.permission;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.usecase.permission.PermissionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

class PermissionUseCaseTest {

  private IPermissionRepository permissionRepository;
  private PermissionUseCase permissionUseCase;

  @BeforeEach
  void setUp() {
    permissionRepository = Mockito.mock(IPermissionRepository.class);
    permissionUseCase = new PermissionUseCase(permissionRepository);
  }

  @Test
  void savePermission_shouldReturnSavedPermission() {
    Permission permission = new Permission.Builder()
      .id(null)
      .resource("resource")
      .action("action")
      .build();

    Mockito.when(permissionRepository.findByResourceAndAction("resource", "action"))
      .thenReturn(Mono.just(false));
    Mockito.when(permissionRepository.save(permission))
      .thenReturn(Mono.just(permission));

    StepVerifier.create(permissionUseCase.savePermission(permission))
      .expectNext(permission)
      .verifyComplete();
  }

  @Test
  void getAllPermissions_shouldReturnAllPermissions() {
    Permission p1 = new Permission.Builder().id(1L).resource("res1").action("act1").build();
    Permission p2 = new Permission.Builder().id(2L).resource("res2").action("act2").build();

    Mockito.when(permissionRepository.findAll())
      .thenReturn(Flux.fromIterable(Arrays.asList(p1, p2)));

    StepVerifier.create(permissionUseCase.getAllPermissions())
      .expectNext(p1)
      .expectNext(p2)
      .verifyComplete();
  }

  @Test
  void findByResourceAndAction_shouldReturnTrueIfExists() {
    Mockito.when(permissionRepository.findByResourceAndAction("res", "act"))
      .thenReturn(Mono.just(true));

    StepVerifier.create(permissionRepository.findByResourceAndAction("res", "act"))
      .expectNext(true)
      .verifyComplete();
  }

  @Test
  void findByResourceAndAction_shouldReturnFalseIfNotExists() {
    Mockito.when(permissionRepository.findByResourceAndAction("res", "act"))
      .thenReturn(Mono.just(false));

    StepVerifier.create(permissionRepository.findByResourceAndAction("res", "act"))
      .expectNext(false)
      .verifyComplete();
  }
}