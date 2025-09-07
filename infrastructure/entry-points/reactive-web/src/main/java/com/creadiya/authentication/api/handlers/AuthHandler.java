package com.creadiya.authentication.api.handlers;

import com.creadiya.authentication.api.dto.SigninDto;
import com.creadiya.authentication.api.dto.ValidateTokenDto;
import com.creadiya.authentication.api.mapper.IAuthMapper;
import com.creadiya.authentication.model.auth.Auth;
import com.creadiya.authentication.usecase.auth.AuthUseCase;
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
public class AuthHandler {
  private final AuthUseCase authUseCase;
  private final IAuthMapper authMapper;

  public Mono<ServerResponse> listenSignIn (ServerRequest serverRequest){
    return serverRequest.bodyToMono(SigninDto.class)
      .flatMap(credentials -> authUseCase.signIn(credentials.username(), credentials.password())
        .flatMap(auth ->
          ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(auth), Auth.class)));
  }

  public  Mono<ServerResponse> validateToken (ServerRequest serverRequest){
    return serverRequest.bodyToMono(ValidateTokenDto.class)
      .flatMap(tokenDTO -> authUseCase.validateToken(tokenDTO.token()))
      .map(authMapper::toResponse)
      .flatMap(user ->
        ServerResponse.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body((Mono.just(user)), ValidateTokenDto.class ));
  }

}