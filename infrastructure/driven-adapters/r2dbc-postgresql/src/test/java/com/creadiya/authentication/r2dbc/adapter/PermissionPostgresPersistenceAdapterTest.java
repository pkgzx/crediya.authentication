package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PermissionPostgresPersistenceAdapterTest {

    private IPermissionPostgresRepository repository;
    private IPermissionPersistenceMapper mapper;
    private TransactionalOperator transactionalOperator;
    private PermissionPostgresPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(IPermissionPostgresRepository.class);
        mapper = mock(IPermissionPersistenceMapper.class);
        transactionalOperator = mock(TransactionalOperator.class);
        adapter = new PermissionPostgresPersistenceAdapter(repository, mapper, transactionalOperator);

        // Simula el comportamiento del operador transaccional
        when(transactionalOperator.transactional(any(Mono.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void save_shouldReturnSavedPermission() {
        Permission permission = new Permission();
        PermissionEntity entity = new PermissionEntity();
        entity.setId(1L);

        when(mapper.toEntity(permission)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toModel(entity)).thenReturn(permission);

        StepVerifier.create(adapter.save(permission))
            .expectNext(permission)
            .verifyComplete();
    }

    @Test
    void findByResourceAndAction_shouldReturnTrueIfExists() {
        PermissionEntity entity = new PermissionEntity();
        when(repository.findByResourceAndAction("resource", "action"))
            .thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.findByResourceAndAction("resource", "action"))
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    void findByResourceAndAction_shouldReturnFalseIfNotExists() {
        when(repository.findByResourceAndAction("resource", "action"))
            .thenReturn(Mono.empty());

        StepVerifier.create(adapter.findByResourceAndAction("resource", "action"))
            .expectNext(false)
            .verifyComplete();
    }

    @Test
    void findById_shouldReturnPermissionIfExists() {
        PermissionEntity entity = new PermissionEntity();
        Permission permission = new Permission();
        when(repository.findById(1L)).thenReturn(Mono.just(entity));
        when(mapper.toModel(entity)).thenReturn(permission);

        StepVerifier.create(adapter.findById(1L))
            .expectNext(permission)
            .verifyComplete();
    }

    @Test
    void findById_shouldReturnEmptyIfNotExists() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findById(1L))
            .verifyComplete();
    }

    @Test
    void findAll_shouldReturnAllPermissions() {
        PermissionEntity entity1 = new PermissionEntity();
        PermissionEntity entity2 = new PermissionEntity();
        Permission permission1 = new Permission();
        Permission permission2 = new Permission();

        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));
        when(mapper.toModel(entity1)).thenReturn(permission1);
        when(mapper.toModel(entity2)).thenReturn(permission2);

        StepVerifier.create(adapter.findAll())
            .expectNext(permission1)
            .expectNext(permission2)
            .verifyComplete();
    }
}