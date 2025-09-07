package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreatePermissionDto;
import com.creadiya.authentication.api.mapper.IPermissionMapper;
import com.creadiya.authentication.api.validation.PermissionValidator;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
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
public class PermissionHandler {
  private final IPermissionServicePort permissionServicePort;
  private final IPermissionMapper permissionMapper;
  private final PermissionValidator permissionValidator;

  public Mono<ServerResponse> listenCreatePermission(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreatePermissionDto.class)
      .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.REQUEST_BODY_EMPTY)))
      .map(permissionMapper::toModel)
      .flatMap(permission -> permissionValidator.validateResource(permission)
                                                          .then(permissionValidator.validateAction(permission)
                                                          .then(Mono.just(permission)))
      )
      .flatMap(permissionServicePort::savePermission)
      .doOnSuccess(franchise -> log.info("Franchise registered successfully"))
      .flatMap(savedPermission -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(savedPermission), Permission.class));
  }

  public Mono<ServerResponse> getAllPermissions() {
    return ServerResponse.ok().body(permissionServicePort.getAllPermissions().collectList(), Object.class);
  }


}
