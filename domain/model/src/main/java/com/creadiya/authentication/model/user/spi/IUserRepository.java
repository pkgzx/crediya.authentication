package com.creadiya.authentication.model.user.spi;

import com.creadiya.authentication.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository {
  Mono<User> save(User user);
  Mono<User> findByUsername(String username);
  Flux<User> findAll();
  Mono<User> findByIdentification(String identification);
  Mono<User> findByPhone(String phone);
}
