package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.mapper.IRoleMapper;
import com.creadiya.authentication.api.validation.RoleValidator;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleHandler {
  private final IRoleServicePort roleServicePort;
  private final IRoleMapper roleMapper;
  private final RoleValidator roleValidator;

  public Mono<ServerResponse> listenCreateRole(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateRoleDto.class)
      .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.REQUEST_BODY_EMPTY)))
      .map(roleMapper::toModel)
      .flatMap(role -> roleValidator.validateName(role)
                                         .then(roleValidator.validateDescription(role)
                                         .then(Mono.just(role)))
      )
      .flatMap(roleServicePort::saveRole)
      .doOnSuccess(role -> log.info("Role registered successfully"))
      .flatMap(savedRole -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(savedRole), Role.class));
  }

public Mono<ServerResponse> getAllRoles() {
    return roleServicePort.getAllRoles()
        .collectList()
        .flatMap(roles -> {
            if (roles.isEmpty()) {
                return ServerResponse.noContent().build();
            }
            return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(roles);
        });

}


}
