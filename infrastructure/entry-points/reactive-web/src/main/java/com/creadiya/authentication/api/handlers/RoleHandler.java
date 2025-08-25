package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateRoleDto;
import com.creadiya.authentication.api.dto.ErrorDto;
import com.creadiya.authentication.api.dto.UpdateRoleDto;
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
      .flatMap(savedRole -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(savedRole), Role.class));
  }

  public Mono<ServerResponse> getAllRoles() {
    return ServerResponse.ok().body(roleServicePort.getAllRoles(), Object.class);
  }

  public Mono<ServerResponse> updateRole(ServerRequest serverRequest) {
    String idParam = serverRequest.pathVariable("id");
    Long roleId;
    try {
      roleId = Long.valueOf(idParam);
    } catch (NumberFormatException ex) {
      log.error("Invalid role id format: {}", idParam);
      return ErrorBuilder.<Role>buildErrorResponse(
        HttpStatus.BAD_REQUEST,
        List.of(ErrorDto.builder()
          .code(TechnicalMessage.INVALID_PARAM.getCode())
          .message(TechnicalMessage.INVALID_PARAM.getMessage())
          .build()));
    }

    return serverRequest.bodyToMono(UpdateRoleDto.class)
      .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.REQUEST_BODY_EMPTY)))
      .map(dto -> roleMapper.toModel(dto, Integer.valueOf(roleId.toString())))
      .flatMap(role -> {
        role.setId(roleId);
        return roleServicePort.updateRole(role);
      })
      .doOnSuccess(role -> log.info("Role updated successfully"))
      .flatMap(updatedRole -> ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(updatedRole), Role.class));
  }
}
