package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IPermissionPostgresRepository extends ReactiveCrudRepository<PermissionEntity, Long>,
  ReactiveQueryByExampleExecutor<PermissionEntity> {

  Mono<PermissionEntity> findByResourceAndAction(String resource, String action);
}
