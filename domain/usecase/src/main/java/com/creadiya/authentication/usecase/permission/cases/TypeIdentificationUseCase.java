package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
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
      .then(checkTypeIdentificationExists(typeIdentifaction)
      )
      .then(typeIdentificationRepository.save(typeIdentifaction)
      );
  }

  @Override
  public Flux<TypeIdentification> getAllTypeIdentifications() {
    return typeIdentificationRepository.findAll();
  }

  @Override
  public Mono<TypeIdentification> getTypeIdentificationById(Integer id) {
    return typeIdentificationRepository.findById(id);
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
