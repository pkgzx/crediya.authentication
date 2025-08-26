package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.api.handlers.UserHandler;
import com.creadiya.authentication.api.mapper.IUserMapper;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.usecase.permission.api.IUserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

class UserHandlerTest {

  private IUserServicePort userServicePort;
  private IUserMapper userMapper;
  private UserHandler userHandler;

  @BeforeEach
  void setUp() {
    userServicePort = Mockito.mock(IUserServicePort.class);
    userMapper = Mockito.mock(IUserMapper.class);
    userHandler = new UserHandler(userServicePort, userMapper);
  }

  @Test
  void createUser_shouldReturnCreatedResponse() {
    CreateUserDto dto = new CreateUserDto();
    dto.setEmail("test@example.com");
    dto.setName("John");
    dto.setLastName("Doe");
    User user = new User.Builder().email("test@example.com").name("John").lastName("Doe").build();

    ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
    Mockito.when(serverRequest.bodyToMono(CreateUserDto.class)).thenReturn(Mono.just(dto));
    Mockito.when(userMapper.toModel(dto)).thenReturn(user);
    Mockito.when(userServicePort.createUser(user)).thenReturn(Mono.just(user));

    Mono<ServerResponse> responseMono = userHandler.createUser(serverRequest);

    StepVerifier.create(responseMono)
      .expectNextMatches(response -> response.statusCode().value() == 201 && Objects.equals(response.headers().getContentType(), MediaType.APPLICATION_JSON))
      .verifyComplete();
  }

  @Test
  void createUser_shouldReturnErrorIfBodyEmpty() {
    ServerRequest serverRequest = Mockito.mock(ServerRequest.class);
    Mockito.when(serverRequest.bodyToMono(CreateUserDto.class)).thenReturn(Mono.empty());

    Mono<ServerResponse> responseMono = userHandler.createUser(serverRequest);

    StepVerifier.create(responseMono)
      .expectComplete()
            .verify();
  }
}