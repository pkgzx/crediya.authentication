package com.creadiya.authentication.usecase.permission.api;

import com.creadiya.authentication.model.permission.Permission;
import reactor.core.publisher.Mono;

public interface IPermissionServicePort {
  public Mono<Permission> savePermission(Permission permission);
}
