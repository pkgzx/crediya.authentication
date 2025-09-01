package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.RoleEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IRolePostgresRepository extends ReactiveCrudRepository<RoleEntity, Long>, ReactiveQueryByExampleExecutor<RoleEntity> {
  Mono<RoleEntity> findByName(String name);
}
