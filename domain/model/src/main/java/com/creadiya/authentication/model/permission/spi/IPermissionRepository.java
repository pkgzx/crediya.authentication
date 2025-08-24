package com.creadiya.authentication.model.permission.spi;

import com.creadiya.authentication.model.permission.Permission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IPermissionRepository {
    Mono<Permission> save(Permission permission);

    Flux<Permission> findAll();

    Mono<Permission> findById(Long id);

    Mono<Boolean> findByResourceAndAction(String resource, String action);
}
