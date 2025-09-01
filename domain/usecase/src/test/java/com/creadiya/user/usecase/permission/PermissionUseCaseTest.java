package com.creadiya.user.usecase.permission;


import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.usecase.permission.cases.PermissionUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionUseCaseTest {

  // Mock dependencies -> PermissionRepository, PermissionValidator
  @Mock
  private IPermissionRepository permissionRepository;



  private PermissionUseCase permissionUseCase;

  @BeforeEach
   void setup(){
    permissionUseCase = new PermissionUseCase(permissionRepository);
  }


  @Test
   void validateSavePermissionSuccess(){
    // Input data: {action: "read", resource: "user"} -> Permission
    Permission input =  Permission.builder()
                                  .action("read")
                                  .resource("user")
                                  .build();

    Permission output = Permission.builder()
                                  .id(1L)
                                  .action("read")
                                  .resource("user")
                                  .build();


    // Mock called methods
    when(permissionRepository.findByResourceAndAction(input.getResource(), input.getAction())).thenReturn(Mono.just(false));
    when(permissionRepository.save(input)).thenReturn(Mono.just(output));

    StepVerifier.create(permissionUseCase.savePermission(input))
      .expectNext(output)
      .verifyComplete();

  }

  @Test
  void validateGetAllPermissionsSuccess(){
    Permission[] output = {Permission.builder().id(1L).build(), Permission.builder().id(2L).build()};

    when(permissionRepository.findAll()).thenReturn(Flux.just(output));

    StepVerifier.create(permissionUseCase.getAllPermissions())
      .expectNext(output)
      .verifyComplete();
  }

  @Test
  void validateGetPermissionByIdSuccess(){
    Permission output = Permission.builder().id(1L).build();
    Long input = 1L;
    when(permissionRepository.findById(input)).thenReturn(Mono.just(output));

    StepVerifier.create(permissionUseCase.getPermissionById(input))
      .expectNext(output)
      .verifyComplete();

  }

}