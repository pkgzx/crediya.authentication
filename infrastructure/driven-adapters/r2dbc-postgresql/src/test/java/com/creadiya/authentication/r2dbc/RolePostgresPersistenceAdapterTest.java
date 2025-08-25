package com.creadiya.authentication.r2dbc;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.r2dbc.adapter.PermissionPostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.adapter.RolePostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.entity.RoleEntity;
import com.creadiya.authentication.r2dbc.entity.RolePermissionEntity;
import com.creadiya.authentication.r2dbc.mapper.IRolePersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IRolePermissionPostgresRepository;
import com.creadiya.authentication.r2dbc.repository.IRolePostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;


class RolePostgresPersistenceAdapterTest {

  private IRolePostgresRepository roleRepo;
  private IRolePermissionPostgresRepository rolePermRepo;
  private IRolePersistenceMapper roleMapper;
  private PermissionPostgresPersistenceAdapter permAdapter;
  private TransactionalOperator transactionalOperator;
  private RolePostgresPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    roleRepo = Mockito.mock(IRolePostgresRepository.class);
    rolePermRepo = Mockito.mock(IRolePermissionPostgresRepository.class);
    roleMapper = Mockito.mock(IRolePersistenceMapper.class);
    permAdapter = Mockito.mock(PermissionPostgresPersistenceAdapter.class);
    transactionalOperator = Mockito.mock(TransactionalOperator.class);

    Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Mockito.when(transactionalOperator.transactional(Mockito.any(Flux.class))).thenAnswer(invocation -> invocation.getArgument(0));

    adapter = new RolePostgresPersistenceAdapter(roleRepo, rolePermRepo, roleMapper, permAdapter, transactionalOperator);
  }

 @Test
 void save_shouldReturnSavedRoleWithPermissions() {
   Permission permission = new Permission.Builder().id(1L).resource("res").action("act").build();
   Role role = new Role.Builder().id(null).name("admin").description("desc").permissions(Collections.singletonList(permission)).build();
   Role savedRole = new Role.Builder().id(10L).name("admin").description("desc").build();

   RoleEntity roleEntity = new RoleEntity();
   Mockito.when(roleMapper.toEntity(role)).thenReturn(roleEntity);
   Mockito.when(roleRepo.save(roleEntity)).thenReturn(Mono.just(roleEntity));
   Mockito.when(roleMapper.toModel(roleEntity)).thenReturn(savedRole);

   RolePermissionEntity rolePermEntity = Mockito.mock(RolePermissionEntity.class);
   Mockito.when(roleMapper.toRolePermissionEntity(10L, 1L)).thenReturn(rolePermEntity);
   Mockito.when(rolePermRepo.save(rolePermEntity)).thenReturn(Mono.just(rolePermEntity));
   Mockito.when(rolePermRepo.findAllByRoleId(10L)).thenReturn(Flux.just(rolePermEntity));
   Mockito.when(rolePermEntity.getPermissionId()).thenReturn(1L);
   Mockito.when(permAdapter.findById(1L)).thenReturn(Mono.just(permission));

   StepVerifier.create(adapter.save(role))
     .expectNextMatches(r -> r.getId().equals(10L) && r.getPermissions().size() == 1)
     .verifyComplete();
 }

 @Test
 void findAll_shouldReturnRolesWithPermissions() {
   RoleEntity roleEntity = new RoleEntity();
   Role role = new Role.Builder().id(10L).name("admin").description("desc").build();
   RolePermissionEntity rolePermEntity = Mockito.mock(RolePermissionEntity.class);
   Permission permission = new Permission.Builder().id(1L).resource("res").action("act").build();

   Mockito.when(roleRepo.findAll()).thenReturn(Flux.just(roleEntity));
   Mockito.when(roleMapper.toModel(roleEntity)).thenReturn(role);
   Mockito.when(rolePermRepo.findAllByRoleId(10L)).thenReturn(Flux.just(rolePermEntity));
   Mockito.when(rolePermEntity.getPermissionId()).thenReturn(1L);
   Mockito.when(permAdapter.findAll()).thenReturn(Flux.just(permission));

   StepVerifier.create(adapter.findAll())
     .expectNextMatches(r -> r.getId().equals(10L) && r.getPermissions().size() == 1)
     .verifyComplete();
 }
  @Test
  void findByName_shouldReturnTrueIfExists() {
    RoleEntity roleEntity = new RoleEntity();
    Mockito.when(roleRepo.findByName("admin")).thenReturn(Mono.just(roleEntity));

    StepVerifier.create(adapter.findByName("admin"))
      .expectNext(true)
      .verifyComplete();
  }

  @Test
  void findByName_shouldReturnFalseIfNotExists() {
    Mockito.when(roleRepo.findByName("admin")).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByName("admin"))
      .expectNext(false)
      .verifyComplete();
  }
}