package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.permission.gateways.IPermissionRepository;
import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.r2dbc.helper.ReactiveAdapterOperations;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adapter class that implements the IPermissionRepository interface using a reactive repository.
 */
@Repository
public class PermissionPostgresRepositoryAdapter extends ReactiveAdapterOperations<Permission, PermissionEntity, Long
  , IPermissionPostgresRepository> implements IPermissionRepository {

  public PermissionPostgresRepositoryAdapter(IPermissionPostgresRepository repository, ObjectMapper mapper){
    super(repository, mapper, d -> mapper.map(d, Permission.class));
  }

  @Override
  @Transactional
  public Mono<Permission> save(Permission permission) {
    return super.save(permission);
  }

  @Override
  public Flux<Permission> finAdll() {
    return super.findAll();
  }
}
