package com.creadiya.authentication.usecase.permission;

import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.permission.spi.IPermissionRepository;

import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.validation.PermissionValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PermissionUseCase implements IPermissionServicePort {
    private final IPermissionRepository permissionRepository;

    public PermissionUseCase(IPermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Mono<Permission> savePermission(Permission permission) {
      return PermissionValidator.validateResource(permission)
          .then(PermissionValidator.validateAction(permission))
          .then(checkPermissionExists(permission))
          .then(permissionRepository.save(permission));
    }

    public Flux<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    private Mono<Void> checkPermissionExists(Permission permission) {
        return permissionRepository.findByResourceAndAction(permission.getResource(), permission.getAction())
          .flatMap(exist -> {
            if (Boolean.TRUE.equals(exist)) {
              return Mono.error(new BusinessException(TechnicalMessage.PERMISSION_ALREADY_EXISTS));
            }
            return Mono.empty();
          });
    }
}
