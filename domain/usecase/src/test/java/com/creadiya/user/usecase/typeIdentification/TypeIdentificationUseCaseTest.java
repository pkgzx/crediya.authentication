package com.creadiya.user.usecase.typeIdentification;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.usecase.permission.cases.TypeIdentificationUseCase;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TypeIdentificationUseCaseTest {

  private ITypeIdentificationRepository typeIdentificationRepository;
  private TypeIdentificationUseCase typeIdentificationUseCase;

  @BeforeEach
  void setUp() {
    typeIdentificationRepository = Mockito.mock(ITypeIdentificationRepository.class);
    typeIdentificationUseCase = new TypeIdentificationUseCase(typeIdentificationRepository);
  }

  @Test
  void createTypeIdentification_shouldReturnSavedTypeIdentification() {
    TypeIdentification ti = new TypeIdentification.Builder().id(1L).name("DNI").build();

    // Java
    Mockito.when(typeIdentificationRepository.findByName("DNI")).thenReturn(Mono.empty());
    Mockito.when(typeIdentificationRepository.save(ti)).thenReturn(Mono.just(ti));

    StepVerifier.create(typeIdentificationUseCase.createTypeIdentification(ti))
      .expectNext(ti)
      .verifyComplete();
  }

  @Test
  void createTypeIdentification_shouldThrowIfTypeIdentificationExists() {
    TypeIdentification ti = new TypeIdentification.Builder().id(1L).name("DNI").build();

    Mockito.when(typeIdentificationRepository.findByName("DNI")).thenReturn(Mono.just(ti));
    Mockito.when(typeIdentificationRepository.save(Mockito.any())).thenReturn(Mono.just(ti));

    StepVerifier.create(typeIdentificationUseCase.createTypeIdentification(ti))
      .expectErrorMatches(e -> e instanceof BusinessException &&
        ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.TYPE_IDENTIFICATION_ALREADY_EXISTS)
      .verify();
  }

  @Test
  void getAllTypeIdentifications_shouldReturnAll() {
    TypeIdentification ti1 = new TypeIdentification.Builder().id(1L).name("DNI").build();
    TypeIdentification ti2 = new TypeIdentification.Builder().id(2L).name("NIE").build();

    Mockito.when(typeIdentificationRepository.findAll()).thenReturn(Flux.just(ti1, ti2));

    StepVerifier.create(typeIdentificationUseCase.getAllTypeIdentifications())
      .expectNext(ti1)
      .expectNext(ti2)
      .verifyComplete();
  }
}