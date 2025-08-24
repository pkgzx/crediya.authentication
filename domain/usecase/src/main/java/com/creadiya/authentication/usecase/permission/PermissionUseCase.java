package com.creadiya.authentication.usecase.permission;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;

import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PermissionUseCase implements IPermissionServicePort {
    private final IPermissionRepository permissionRepository;

    public PermissionUseCase(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Mono<Permission> savePermission(Permission permission) {
      if (permission.getResource() == null || permission.getResource().isBlank()
          ||  permission.getAction() == null || permission.getAction().isBlank()) {
        return Mono.error(new IllegalArgumentException("Required fields are missing"));
      }
        return permissionRepository.save(permission);
    }

    public Mono<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public Flux<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}
