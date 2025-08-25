package com.creadiya.authentication.usecase.permission.api;

import com.creadiya.authentication.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserServicePort {
  Mono<User> createUser(User user);
  Mono<User> getUserByUsername(String username);
  Flux<User> getAllUsers();
}
