package com.creadiya.authentication.r2dbc;


import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.r2dbc.adapter.PermissionPostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PermissionPostgresPersistenceAdapterTest {

  private IPermissionPostgresRepository repository;
  private IPermissionPersistenceMapper mapper;
  private PermissionPostgresPersistenceAdapter adapter;
  private TransactionalOperator transactionalOperator;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(IPermissionPostgresRepository.class);
    mapper = Mockito.mock(IPermissionPersistenceMapper.class);

    transactionalOperator = Mockito.mock(TransactionalOperator.class);

    Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Mockito.when(transactionalOperator.transactional(Mockito.any(Flux.class))).thenAnswer(invocation -> invocation.getArgument(0));

    adapter = new PermissionPostgresPersistenceAdapter(repository, mapper, transactionalOperator);
  }

  @Test
  void save_shouldReturnSavedPermission() {
    Permission permission = new Permission.Builder().id(1L).resource("res").action("act").build();
    PermissionEntity entity = new PermissionEntity(); // Simula la entidad

    Mockito.when(mapper.toEntity(permission)).thenReturn(entity);
    Mockito.when(repository.save(entity)).thenReturn(Mono.just(entity));
    Mockito.when(mapper.toModel(entity)).thenReturn(permission);

    StepVerifier.create(adapter.save(permission))
      .expectNext(permission)
      .verifyComplete();
  }

  @Test
  void findByResourceAndAction_shouldReturnTrueIfExists() {
    PermissionEntity entity = new PermissionEntity();
    Mockito.when(repository.findByResourceAndAction("res", "act")).thenReturn(Mono.just(entity));

    StepVerifier.create(adapter.findByResourceAndAction("res", "act"))
      .expectNext(true)
      .verifyComplete();
  }

  @Test
  void findByResourceAndAction_shouldReturnFalseIfNotExists() {
    Mockito.when(repository.findByResourceAndAction("res", "act")).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByResourceAndAction("res", "act"))
      .expectNext(false)
      .verifyComplete();
  }

  @Test
  void findAll_shouldReturnAllPermissions() {
    PermissionEntity entity1 = new PermissionEntity();
    PermissionEntity entity2 = new PermissionEntity();
    Permission p1 = new Permission.Builder().id(1L).resource("r1").action("a1").build();
    Permission p2 = new Permission.Builder().id(2L).resource("r2").action("a2").build();

    Mockito.when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));
    Mockito.when(mapper.toModel(Mockito.any())).thenReturn(p1, p2);

    StepVerifier.create(adapter.findAll())
      .expectNext(p1)
      .expectNext(p2)
      .verifyComplete();
  }
}