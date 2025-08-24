package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Adapter class that implements the IPermissionRepository interface using a reactive repository.
 */
@Slf4j
@RequiredArgsConstructor
public class PermissionPostgresPersistenceAdapter implements IPermissionRepository {

  private final IPermissionPostgresRepository repository;
  private final IPermissionPersistenceMapper mapper;


  @Override
  @Transactional
  public Mono<Permission> save(Permission permission) {
    log.info("Id before saving: {}", permission.getId());
    return repository.save(mapper.toEntity(permission))
      .doOnNext(e -> log.info("Permission saved with id: {}", e.getId()))
      .map(mapper::toModel);
  }

  @Override
  public Mono<Boolean> findByResourceAndAction(String resource, String action) {
     return repository.findByResourceAndAction(resource, action)
      .map(permissionEntity -> true)
      .defaultIfEmpty(false);
  }

  @Override
  public Flux<Permission> findAll() {
    return repository.findAll()
      .map(mapper::toModel);
  }


}
