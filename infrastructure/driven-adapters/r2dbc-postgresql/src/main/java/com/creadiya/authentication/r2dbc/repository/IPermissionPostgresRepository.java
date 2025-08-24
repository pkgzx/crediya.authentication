package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.PermissionEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IPermissionPostgresRepository extends ReactiveCrudRepository<PermissionEntity, Long>,
  ReactiveQueryByExampleExecutor<PermissionEntity> {
}
