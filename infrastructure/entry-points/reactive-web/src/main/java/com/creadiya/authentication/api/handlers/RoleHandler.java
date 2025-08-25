package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.dto.ErrorDto;
import com.creadiya.authentication.api.mapper.IRoleMapper;
import com.creadiya.authentication.api.util.ErrorBuilder;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleHandler {
  private final IRoleServicePort roleServicePort;
  private final IRoleMapper roleMapper;

  public Mono<ServerResponse> listenCreateRole(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateRoleDto.class)
      .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.REQUEST_BODY_EMPTY)))
      .map(roleMapper::toModel)
      .flatMap(roleServicePort::saveRole)
      .doOnSuccess(role -> log.info("Role registered successfully"))
      .flatMap(savedRole -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(savedRole), Role.class))
      .onErrorResume(BusinessException.class,
        ex -> ErrorBuilder.<Role>buildErrorResponse(HttpStatus.resolve(ex.getTechnicalMessage().getCode()),
          List.of(ErrorDto.builder().message(ex.getMessage()).build())))
      .onErrorResume(ex -> {
        log.error("Unexpected error occurred", ex);
        return ErrorBuilder.<Role>buildErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          List.of(ErrorDto.builder()
            .code(TechnicalMessage.INTERNAL_ERROR.getCode())
            .message(TechnicalMessage.INTERNAL_ERROR.getMessage())
            .build()));
      });
  }

  public Flux<Role> getAllRoles() {
    return roleServicePort.getAllRoles();
  }
}
