package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.validation.RoleValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RoleUseCase implements IRoleServicePort {
  private final IRoleRepository roleRepository;
  private final IPermissionServicePort permissionServicePort;

  public RoleUseCase(IRoleRepository roleRepository, IPermissionServicePort permissionServicePort) {
    this.permissionServicePort = permissionServicePort;
    this.roleRepository = roleRepository;
  }

  @Override
  public Mono<Role> saveRole(Role role) {
    return RoleValidator.validateName(role)
      .then(RoleValidator.validateDescription(role))
      .then(checkRoleExists(role))
      .then(checkPermissionsExist(role))
      .then(roleRepository.save(role));
  }

  @Override
  public Flux<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  private Mono<Void> checkRoleExists(Role role) {
    return roleRepository.findByName(role.getName())
      .flatMap(exist -> {
        if (Boolean.TRUE.equals(exist)) {
          return Mono.error(new BusinessException(TechnicalMessage.ROLE_ALREADY_EXISTS));
        }
        return Mono.empty();
      });
  }

  private Mono<Void> checkPermissionsExist(Role role) {
    if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
      return Mono.empty();
    }
    return Flux.fromIterable(role.getPermissions())
      .flatMap(permission -> permissionServicePort.getPermissionById(permission.getId())
        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PERMISSION_NOT_FOUND))))
      .then();
  }
}
