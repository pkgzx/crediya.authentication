package com.creadiya.user.usecase.role;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import com.creadiya.authentication.usecase.permission.cases.RoleUseCase;
import com.creadiya.authentication.model.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {
  @Mock
  private  IRoleRepository roleRepository;
  @Mock
  private  IPermissionServicePort permissionServicePort;

  private RoleUseCase roleUseCase;

  @BeforeEach
  void setUp() {
    roleUseCase = new RoleUseCase(roleRepository, permissionServicePort);
  }

  @Test
  void validSaveRoleSuccess(){
    Permission permission = Permission.builder().id(1L).build();
    Role input = Role.builder()
      .name("ADMIN")
      .description("ADMIN")
      .permissions(List.of(permission))
      .build();

    when(roleRepository.findByName(input.getName())).thenReturn(Mono.just(false));
    when(roleRepository.save(input)).thenReturn(Mono.just(input));


    StepVerifier.create(roleUseCase.saveRole(input))
      .expectNext(input)
      .verifyComplete();

  }

  @Test
  void validListAllRoleSuccess(){
    Role[] roles = {Role.builder().name("ADMIN").description("ADMIN").build()};

    when(roleRepository.findAll()).thenReturn(Flux.just(roles));

    StepVerifier.create(roleUseCase.getAllRoles())
      .expectNext(roles)
      .verifyComplete();
  }

  @Test
  void validGetByIdRoleSuccess(){
    Role role = Role.builder().id(1L).build();
    when(roleRepository.findById(role.getId())).thenReturn(Mono.just(role));
    StepVerifier.create(roleUseCase.getRoleById(role.getId()))
      .expectNext(role)
      .verifyComplete();
  }

  @Test
  void validIfExistsRoleThrowsException(){
    Permission permission = Permission.builder().id(1L).build();
    Role role = Role.builder().id(1L).name("ADMIN")
      .permissions(List.of(permission))
      .build();
    when(roleRepository.findByName(role.getName())).thenReturn(Mono.just(true));
    when(roleRepository.save(role)).thenReturn(Mono.just(role));

    StepVerifier.create(roleUseCase.saveRole(role))
      .expectError()
      .verify();
  }



@Test
void checkPermissionsExist_withEmptyPermissions_returnsEmptyMono() {
    Role role = Role.builder().permissions(List.of()).build();
    Mono<Void> result = roleUseCase.checkPermissionsExist(role);
    StepVerifier.create(result)
        .verifyComplete();
}

@Test
void checkPermissionsExist_withAllPermissionsFound_returnsEmptyMono() {
    Permission permission = Permission.builder().id(1L).build();
    Role role = Role.builder().permissions(List.of(permission)).build();
    when(permissionServicePort.getPermissionById(permission.getId())).thenReturn(Mono.just(permission));

    Mono<Void> result = roleUseCase.checkPermissionsExist(role);
    StepVerifier.create(result)
        .verifyComplete();
}

@Test
void checkPermissionsExist_withMissingPermission_throwsBusinessException() {
    Permission permission = Permission.builder().id(1L).build();
    Role role = Role.builder().permissions(List.of(permission)).build();
    when(permissionServicePort.getPermissionById(permission.getId())).thenReturn(Mono.empty());

    Mono<Void> result = roleUseCase.checkPermissionsExist(role);
    StepVerifier.create(result)
        .expectError(BusinessException.class)
        .verify();
}

  @Test
  void checkPermissionsExist_withNullPermissions_returnsEmptyMono() {
    Role role = Role.builder().permissions(null).build();
    Mono<Void> result = roleUseCase.checkPermissionsExist(role);
    StepVerifier.create(result)
      .verifyComplete();
  }


}
