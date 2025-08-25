package com.creadiya.authentication.usecase.permission.api;

import com.creadiya.authentication.model.typeidentification.TypeIdentification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITypeIdentificationServicePort {
  Mono<TypeIdentification> createTypeIdentification(TypeIdentification typeIdentifaction);
  Flux<TypeIdentification> getAllTypeIdentifications();
}
