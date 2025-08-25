package com.creadiya.authentication.usecase.permission.api;

import com.creadiya.authentication.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRoleServicePort {
  Mono<Role> saveRole(Role role);
  Flux<Role> getAllRoles();
}
