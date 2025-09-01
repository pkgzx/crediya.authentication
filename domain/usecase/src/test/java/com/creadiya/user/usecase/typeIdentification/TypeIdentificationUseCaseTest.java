package com.creadiya.user.usecase.typeIdentification;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.usecase.permission.cases.TypeIdentificationUseCase;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TypeIdentificationUseCaseTest {

    ITypeIdentificationRepository repository;
    TypeIdentificationUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(ITypeIdentificationRepository.class);
        useCase = new TypeIdentificationUseCase(repository);
    }

@Test
void checkTypeIdentificationExists_withNullExist_returnsEmptyMono() {
    TypeIdentification type = TypeIdentification.builder().name("CC").build();
  when(repository.findByName("CC")).thenReturn(Mono.empty());

    StepVerifier.create(useCase.checkTypeIdentificationExists(type))
        .verifyComplete();
}


    @Test
    void createTypeIdentification_alreadyExists() {
        TypeIdentification type = TypeIdentification.builder().name("CC").build();
        when(repository.findByName("CC")).thenReturn(Mono.just(type));


            StepVerifier.create(useCase.createTypeIdentification(type))
                .expectErrorMatches(e -> e instanceof BusinessException &&
                    ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.TYPE_IDENTIFICATION_ALREADY_EXISTS)
                .verify();
    }



    @Test
    void getAllTypeIdentifications_returnsFlux() {
        TypeIdentification type1 = TypeIdentification.builder().name("CC").build();
        TypeIdentification type2 = TypeIdentification.builder().name("TI").build();
        when(repository.findAll()).thenReturn(Flux.just(type1, type2));

        StepVerifier.create(useCase.getAllTypeIdentifications())
            .expectNext(type1)
            .expectNext(type2)
            .verifyComplete();
    }



    @Test
    void getTypeIdentificationById_notFound() {
        when(repository.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.getTypeIdentificationById(1))
            .verifyComplete();
    }
}