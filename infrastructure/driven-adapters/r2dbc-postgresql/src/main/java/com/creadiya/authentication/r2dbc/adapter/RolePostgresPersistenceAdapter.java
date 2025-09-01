package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.r2dbc.entity.RolePermissionEntity;
import com.creadiya.authentication.r2dbc.mapper.IRolePersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IRolePermissionPostgresRepository;
import com.creadiya.authentication.r2dbc.repository.IRolePostgresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class RolePostgresPersistenceAdapter implements IRoleRepository {
  private final IRolePostgresRepository rolePostgresRepository;
  private final IRolePermissionPostgresRepository rolePermissionPostgresRepository;
  private final IRolePersistenceMapper roleMapper;
  private final PermissionPostgresPersistenceAdapter permissionAdapter;
  private final TransactionalOperator transactionalOperator;


  @Override
  public Mono<Role> save(Role role) {
    return rolePostgresRepository.save(roleMapper.toEntity(role))
      .doOnNext(e -> log.info("Role saved with id: {}", e.getId()))
      .map(roleMapper::toModel)
      .flatMap(savedRole -> {
        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
          return Flux.fromIterable(role.getPermissions())
            .flatMap(permission -> {
              RolePermissionEntity rolePermissionEntity = roleMapper.toRolePermissionEntity(savedRole.getId(), permission.getId());
              return rolePermissionPostgresRepository.save(rolePermissionEntity);
            })
            .thenMany(rolePermissionPostgresRepository.findAllByRoleId(savedRole.getId()))
            .flatMap(rolePermissionEntity -> permissionAdapter.findById(rolePermissionEntity.getPermissionId()))
            .collectList()
            .map(permissions -> {
              savedRole.setPermissions(permissions);
              return savedRole;
            });
        }
        return Mono.just(savedRole);
      }
    ).as(transactionalOperator::transactional);
  }
  @Override
  public Flux<Role> findAll() {
    return rolePostgresRepository.findAll()
      .map(roleMapper::toModel)
      .flatMap(role -> rolePermissionPostgresRepository.findAllByRoleId(role.getId())
        .flatMap(rolePermissionEntity -> permissionAdapter.findAll()
          .filter(p -> p.getId().equals(rolePermissionEntity.getPermissionId())))
        .collectList()
        .map(permissions -> {
          role.setPermissions(permissions);
          return role;
        }));
  }

  @Override
  public Mono<Boolean> findByName(String name) {
    return rolePostgresRepository.findByName(name)
      .map(roleEntity -> true)
      .defaultIfEmpty(false);
  }



  @Override
  public Mono<Role> findById(Long id) {
    return rolePostgresRepository.findById(id)
      .map(roleMapper::toModel)
      .flatMap(role -> rolePermissionPostgresRepository.findAllByRoleId(role.getId())
        .flatMap(rolePermissionEntity -> permissionAdapter.findById(rolePermissionEntity.getPermissionId()))
        .collectList()
        .map(permissions -> {
          role.setPermissions(permissions);
          return role;
        }) );
  }
}