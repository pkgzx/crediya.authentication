package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public class TypeIdentificationUseCase implements ITypeIdentificationServicePort {
  private final ITypeIdentificationRepository typeIdentificationRepository;

  public TypeIdentificationUseCase(ITypeIdentificationRepository typeIdentificationRepository) {
    this.typeIdentificationRepository = typeIdentificationRepository;
  }

  @Override
  public Mono<TypeIdentification> createTypeIdentification(TypeIdentification typeIdentifaction) {
    return
      checkTypeIdentificationExists(typeIdentifaction)
      .flatMap(v -> typeIdentificationRepository.save(typeIdentifaction)
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

  public Mono<Void> checkTypeIdentificationExists(TypeIdentification typeIdentifaction) {
    return typeIdentificationRepository.findByName(typeIdentifaction.getName())
      .flatMap(exist -> Mono.error(new BusinessException(TechnicalMessage.TYPE_IDENTIFICATION_ALREADY_EXISTS))
      )
      .switchIfEmpty(Mono.<Void>empty()).then();
  }
}
