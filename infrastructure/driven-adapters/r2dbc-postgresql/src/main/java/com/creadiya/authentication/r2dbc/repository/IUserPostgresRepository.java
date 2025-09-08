package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IUserPostgresRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {
  Mono<UserEntity> findByEmail(String email);
  Mono<UserEntity> findByIdentification(String identification);
  Mono<UserEntity> findByPhone(String phone);

  @Override
  @Query("SELECT * FROM \"User\" WHERE id = :id LIMIT 1")
  Mono<UserEntity> findById(String id);
}
