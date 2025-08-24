package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreatePermissionDto;
import com.creadiya.authentication.api.dto.ErrorDto;
import com.creadiya.authentication.api.mapper.IPermissionMapper;
import com.creadiya.authentication.api.util.ErrorBuilder;
import com.creadiya.authentication.model.permission.Permission;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionHandler {
  private final IPermissionServicePort permissionServicePort;
  private final IPermissionMapper permissionMapper;

  public Mono<ServerResponse> listenCreatePermission(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreatePermissionDto.class)
      .switchIfEmpty(Mono.error(new RuntimeException("Song body not found")))
      .map(permissionMapper::toModel)
      .flatMap(permissionServicePort::savePermission)
      .doOnSuccess(franchise -> log.info("Franchise registered successfully"))
      .flatMap(savedPermission -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(savedPermission), Permission.class))
      .onErrorResume(IllegalArgumentException.class, ex -> ErrorBuilder.<Permission>buildErrorResponse(HttpStatus.BAD_REQUEST,
        List.of(ErrorDto.builder().message(ex.getMessage()).build())));

  }




}
