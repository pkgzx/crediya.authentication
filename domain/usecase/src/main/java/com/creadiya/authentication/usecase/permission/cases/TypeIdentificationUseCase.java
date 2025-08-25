package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.typeidentification.TypeIdentification;
import com.creadiya.authentication.model.typeidentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.validation.TypeIdentificationValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class TypeIdentificationUseCase implements ITypeIdentificationServicePort {
  private final ITypeIdentificationRepository typeIdentificationRepository;

  public TypeIdentificationUseCase(ITypeIdentificationRepository typeIdentificationRepository) {
    this.typeIdentificationRepository = typeIdentificationRepository;
  }

  @Override
  public Mono<TypeIdentification> createTypeIdentification(TypeIdentification typeIdentifaction) {
    return TypeIdentificationValidator.validateName(typeIdentifaction)
      .doOnSuccess(v -> System.out.println("validateName OK: " + v))
      .doOnError(e -> System.out.println("validateName ERROR: " + e))
      .then(checkTypeIdentificationExists(typeIdentifaction)
        .doOnSuccess(v -> System.out.println("checkTypeIdentificationExists OK: " + v))
        .doOnError(e -> System.out.println("checkTypeIdentificationExists ERROR: " + e))
      )
      .then(typeIdentificationRepository.save(typeIdentifaction)
        .doOnSuccess(v -> System.out.println("save OK: " + v))
        .doOnError(e -> System.out.println("save ERROR: " + e))
      );
  }

  @Override
  public Flux<TypeIdentification> getAllTypeIdentifications() {
    return typeIdentificationRepository.findAll();
  }

  private Mono<Void> checkTypeIdentificationExists(TypeIdentification typeIdentifaction) {
    return typeIdentificationRepository.findByName(typeIdentifaction.getName())
      .flatMap(exist -> exist != null
        ? Mono.error(new BusinessException(TechnicalMessage.TYPE_IDENTIFICATION_ALREADY_EXISTS))
        : Mono.<Void>empty()
      )
      .switchIfEmpty(Mono.<Void>empty());
  }
}
