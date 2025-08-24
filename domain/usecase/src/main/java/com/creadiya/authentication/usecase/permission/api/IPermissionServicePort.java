package com.creadiya.authentication.usecase.permission.api;

import com.creadiya.authentication.model.permission.Permission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPermissionServicePort {
   Mono<Permission> savePermission(Permission permission);
   Flux<Permission> getAllPermissions();
}
