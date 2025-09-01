package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.r2dbc.entity.RoleEntity;
import com.creadiya.authentication.r2dbc.entity.RolePermissionEntity;
import com.creadiya.authentication.r2dbc.mapper.IRolePersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IRolePermissionPostgresRepository;
import com.creadiya.authentication.r2dbc.repository.IRolePostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RolePostgresPersistenceAdapterTest {

    private IRolePostgresRepository roleRepo;
    private IRolePermissionPostgresRepository rolePermRepo;
    private IRolePersistenceMapper roleMapper;
    private PermissionPostgresPersistenceAdapter permAdapter;
    private TransactionalOperator transactionalOperator;
    private RolePostgresPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        roleRepo = mock(IRolePostgresRepository.class);
        rolePermRepo = mock(IRolePermissionPostgresRepository.class);
        roleMapper = mock(IRolePersistenceMapper.class);
        permAdapter = mock(PermissionPostgresPersistenceAdapter.class);
        transactionalOperator = mock(TransactionalOperator.class);

        adapter = new RolePostgresPersistenceAdapter(roleRepo, rolePermRepo, roleMapper, permAdapter, transactionalOperator);

        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

 @Test
 void save_shouldReturnSavedRoleWithPermissions() {
     Role role = new Role();
     Permission permission = new Permission();
     permission.setId(2L);
     role.setPermissions(List.of(permission));
     RoleEntity roleEntity = new RoleEntity();
     roleEntity.setId(1L);
     Role savedRole = new Role();
     savedRole.setId(1L);

     when(roleMapper.toEntity(role)).thenReturn(roleEntity);
     when(roleRepo.save(roleEntity)).thenReturn(Mono.just(roleEntity));
     when(roleMapper.toModel(roleEntity)).thenReturn(savedRole);
     when(roleMapper.toRolePermissionEntity(anyLong(), anyLong()))
         .thenAnswer(invocation -> {
             Long roleId = invocation.getArgument(0);
             Long permId = invocation.getArgument(1);
             RolePermissionEntity entity = new RolePermissionEntity();
             entity.setRoleId(roleId);
             entity.setPermissionId(permId);
             return entity;
         });
     when(rolePermRepo.save(any(RolePermissionEntity.class)))
         .thenReturn(Mono.just(new RolePermissionEntity()));
     when(rolePermRepo.findAllByRoleId(1L))
         .thenReturn(Flux.just(new RolePermissionEntity() {{
             setPermissionId(2L);
         }}));
     when(permAdapter.findById(2L)).thenReturn(Mono.just(permission));

     StepVerifier.create(adapter.save(role))
         .expectNextMatches(r -> r.getId().equals(1L) && r.getPermissions().size() == 1)
         .verifyComplete();
 }
    @Test
    void findAll_shouldReturnRolesWithPermissions() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        Role role = new Role();
        role.setId(1L);
        Permission permission = new Permission();
        permission.setId(2L);

        when(roleRepo.findAll()).thenReturn(Flux.just(roleEntity));
        when(roleMapper.toModel(roleEntity)).thenReturn(role);
        when(rolePermRepo.findAllByRoleId(1L))
                .thenReturn(Flux.just(new RolePermissionEntity() {{
                    setPermissionId(2L);
                }}));
        when(permAdapter.findAll()).thenReturn(Flux.just(permission));

        StepVerifier.create(adapter.findAll())
                .expectNextMatches(r -> r.getId().equals(1L) && r.getPermissions().size() == 1)
                .verifyComplete();
    }

    @Test
    void findByName_shouldReturnTrueIfExists() {
        when(roleRepo.findByName("admin")).thenReturn(Mono.just(new RoleEntity()));

        StepVerifier.create(adapter.findByName("admin"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void findByName_shouldReturnFalseIfNotExists() {
        when(roleRepo.findByName("admin")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findByName("admin"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnRoleWithPermissions() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        Role role = new Role();
        role.setId(1L);
        Permission permission = new Permission();
        permission.setId(2L);

        when(roleRepo.findById(1L)).thenReturn(Mono.just(roleEntity));
        when(roleMapper.toModel(roleEntity)).thenReturn(role);
        when(rolePermRepo.findAllByRoleId(1L))
                .thenReturn(Flux.just(new RolePermissionEntity() {{
                    setPermissionId(2L);
                }}));
        when(permAdapter.findById(2L)).thenReturn(Mono.just(permission));

        StepVerifier.create(adapter.findById(1L))
                .expectNextMatches(r -> r.getId().equals(1L) && r.getPermissions().size() == 1)
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnEmptyIfNotExists() {
        when(roleRepo.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findById(1L))
                .verifyComplete();
    }


    @Test
    void save_shouldReturnSavedRoleWithoutPermissionsIfNoneProvided() {
        Role role = new Role();
        role.setPermissions(null); // O puedes usar una lista vacía: new ArrayList<>()
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        Role savedRole = new Role();
        savedRole.setId(1L);

        when(roleMapper.toEntity(role)).thenReturn(roleEntity);
        when(roleRepo.save(roleEntity)).thenReturn(Mono.just(roleEntity));
        when(roleMapper.toModel(roleEntity)).thenReturn(savedRole);

        StepVerifier.create(adapter.save(role))
            .expectNextMatches(r -> r.getId().equals(1L) && (r.getPermissions() == null || r.getPermissions().isEmpty()))
            .verifyComplete();
    }

    @Test
    void save_shouldReturnSavedRoleWithoutPermissionsIfEmptyListProvided() {
        Role role = new Role();
        role.setPermissions(new java.util.ArrayList<>());
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        Role savedRole = new Role();
        savedRole.setId(1L);

        when(roleMapper.toEntity(role)).thenReturn(roleEntity);
        when(roleRepo.save(roleEntity)).thenReturn(Mono.just(roleEntity));
        when(roleMapper.toModel(roleEntity)).thenReturn(savedRole);

        StepVerifier.create(adapter.save(role))
            .expectNextMatches(r -> r.getId().equals(1L) && (r.getPermissions() == null || r.getPermissions().isEmpty()))
            .verifyComplete();
    }
}