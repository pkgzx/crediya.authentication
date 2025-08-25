package com.creadiya.authentication.model.role.spi;

import com.creadiya.authentication.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRoleRepository {
  Mono<Role> save(Role role);
  Flux<Role> findAll();
  Mono<Boolean> findByName(String name);
}
