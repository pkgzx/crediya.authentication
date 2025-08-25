package com.creadiya.authentication.r2dbc.repository;

import com.creadiya.authentication.r2dbc.entity.UserTypeIdentificationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserTypeIdentificationPostgresRepository extends ReactiveCrudRepository<UserTypeIdentificationEntity, Long>, ReactiveQueryByExampleExecutor<UserTypeIdentificationEntity> {
}
