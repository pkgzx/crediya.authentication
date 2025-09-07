package com.creadiya.authentication.model.auth.gateway;

import reactor.core.publisher.Mono;

public interface IPasswordEncoder {
  Mono<String> encode(String password);
  Mono<Boolean> matches (String password, String hash);
}
