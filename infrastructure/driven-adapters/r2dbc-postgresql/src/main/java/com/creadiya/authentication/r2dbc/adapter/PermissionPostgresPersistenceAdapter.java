package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adapter class that implements the IPermissionRepository interface using a reactive repository.
 */
@RequiredArgsConstructor
public class PermissionPostgresPersistenceAdapter implements IPermissionRepository {

  private final IPermissionPostgresRepository repository;
  private final IPermissionPersistenceMapper mapper;


  @Override
  @Transactional
  public Mono<Permission> save(Permission permission) {
    return repository.save(mapper.toEntity(permission))
      .map(mapper::toModel);
  }

  @Override
  public Mono<Permission> findById(Long id) {
    return null;
  }

  @Override
  public Flux<Permission> findAll() {
    return repository.findAll()
      .map(mapper::toModel);
  }
}
