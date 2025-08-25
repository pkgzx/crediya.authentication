package com.creadiya.authentication.model.typeIdentification.spi;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;

public interface ITypeIdentificationRepository {
  Mono<TypeIdentification> save(TypeIdentification typeIdentifaction);
  Flux<TypeIdentification> findAll();
  Mono<TypeIdentification> findByName(String name);
  Mono<TypeIdentification> findById(Integer id);
}
