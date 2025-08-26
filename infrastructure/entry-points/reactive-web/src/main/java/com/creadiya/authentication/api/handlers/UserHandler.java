package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.api.mapper.IUserMapper;
import com.creadiya.authentication.usecase.permission.api.IUserServicePort;
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
public class UserHandler {
  private final IUserServicePort userServicePort;
  private final IUserMapper userMapper;

  public Mono<ServerResponse> createUser(ServerRequest request) {
    return request.bodyToMono(CreateUserDto.class)
      .map(userMapper::toModel)
      .flatMap(userServicePort::createUser)
      .flatMap(response -> ServerResponse.status(201)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(response));

  }
}
