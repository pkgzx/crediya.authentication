package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
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
    return checkRoleExists(role)
      .flatMap  (v -> checkPermissionsExist(role))
      .then(roleRepository.save(role));
  }

  @Override
  public Flux<Role> getAllRoles() {
    return roleRepository.findAll();
  }


  @Override
  public Mono<Role> getRoleById(Long id) {
    return roleRepository.findById(id);
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

  public Mono<Void> checkPermissionsExist(Role role) {
    if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
      return Mono.empty();
    }
    return Flux.fromIterable(role.getPermissions())
      .flatMap(permission -> permissionServicePort.getPermissionById(permission.getId())
        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PERMISSION_NOT_FOUND))))
      .then()
      .cast(Void.class);
  }
}
