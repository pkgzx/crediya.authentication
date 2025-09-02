package com.creadiya.authentication.model.auth.gateway;

import com.creadiya.authentication.model.auth.Auth;
import com.creadiya.authentication.model.user.User;
import reactor.core.publisher.Mono;

public interface IAuthProvider {
  Mono<Auth> generateToken(User user);
  Mono<Boolean> validateToken(Auth token);
}
