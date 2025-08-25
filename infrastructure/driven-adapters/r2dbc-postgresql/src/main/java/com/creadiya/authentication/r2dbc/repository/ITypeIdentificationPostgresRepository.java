package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.TypeIdentificationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ITypeIdentificationPostgresRepository extends ReactiveCrudRepository<TypeIdentificationEntity, Integer>, ReactiveQueryByExampleExecutor<TypeIdentificationEntity> {
  Mono<TypeIdentificationEntity> findByName(String name);
}
