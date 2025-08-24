package com.creadiya.authentication.usecase.permission;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.gateways.IPermissionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PermissionUseCase {
    private final IPermissionRepository permissionRepository;

    public PermissionUseCase(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Mono<Permission> savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Mono<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public Flux<Permission> getAllPermissions() {
        return permissionRepository.finAdll();
    }
}
