package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.api.mapper.IUserMapper;
import com.creadiya.authentication.api.validation.UserValidator;
import com.creadiya.authentication.usecase.permission.cases.UserUseCase;
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
  private final UserUseCase userCase;
  private final IUserMapper userMapper;
  private final UserValidator validator;

  public Mono<ServerResponse> createUser(ServerRequest request) {
    return request.bodyToMono(CreateUserDto.class)
      .map(userMapper::toModel)
      .flatMap(user ->
        validator.validName(user.getName())
          .then(validator.validLastName(user.getLastName()))
          .then(validator.validEmail(user.getEmail()))
          .then(validator.validAddress(user.getAddress()))
          .then(validator.validIdentification(user.getIdentification()))
          .then(validator.validateCurrency(user.getBaseSalary().getCurrency().getCurrencyCode()))
          .then(validator.validPhone(user.getPhone()))
          .then(validator.validOtherEntities(user))
          .then(Mono.just(user))
      )
      .flatMap(userCase::createUser)
      .flatMap(response -> ServerResponse.status(201)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(response));

  }

  public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
    String email = request.pathVariable("email");
    return userCase.getUserByUsername(email)
      .flatMap(user -> ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(user))
      .switchIfEmpty(ServerResponse.notFound().build());
  }
}
