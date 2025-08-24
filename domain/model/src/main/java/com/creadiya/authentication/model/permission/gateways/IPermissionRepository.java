package com.creadiya.authentication.model.permission.gateways;

import com.creadiya.authentication.model.permission.Permission;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPermissionRepository {
    Mono<Permission> save(Permission permission);

    Flux<Permission> finAdll();

    Mono<Permission> findById(Long id);
}
