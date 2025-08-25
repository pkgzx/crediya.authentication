package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.RolePermissionEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IRolePermissionPostgresRepository extends ReactiveCrudRepository<RolePermissionEntity, Long>, ReactiveQueryByExampleExecutor<RolePermissionEntity> {
  Flux<RolePermissionEntity> findAllByRoleId(Long roleId);
}
