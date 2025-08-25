package com.creadiya.authentication.r2dbc;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.r2dbc.adapter.TypeIdentificationPostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.entity.TypeIdentificationEntity;
import com.creadiya.authentication.r2dbc.mapper.ITypeIdentificationPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.ITypeIdentificationPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class TypeIdentificationPostgresPersistenceAdapterTest {

    private ITypeIdentificationPostgresRepository repository;
    private ITypeIdentificationPersistenceMapper mapper;
    private TransactionalOperator transactionalOperator;
    private TypeIdentificationPostgresPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ITypeIdentificationPostgresRepository.class);
        mapper = Mockito.mock(ITypeIdentificationPersistenceMapper.class);
        transactionalOperator = Mockito.mock(TransactionalOperator.class);

        Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(transactionalOperator.transactional(Mockito.any(Flux.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adapter = new TypeIdentificationPostgresPersistenceAdapter(repository, mapper, transactionalOperator);
    }

    @Test
    void save_shouldReturnSavedTypeIdentification() {
        TypeIdentification type = new TypeIdentification.Builder().id(1L).name("DNI").build();
        TypeIdentificationEntity entity = new TypeIdentificationEntity();
        Mockito.when(mapper.toEntity(type)).thenReturn(entity);
        Mockito.when(repository.save(entity)).thenReturn(Mono.just(entity));
        Mockito.when(mapper.toModel(entity)).thenReturn(type);

        StepVerifier.create(adapter.save(type))
            .expectNextMatches(t -> t.getId().equals(1L) && t.getName().equals("DNI"))
            .verifyComplete();
    }

    @Test
    void findAll_shouldReturnTypeIdentifications() {
        TypeIdentificationEntity entity = new TypeIdentificationEntity();
        TypeIdentification type = new TypeIdentification.Builder().id(1L).name("DNI").build();
        Mockito.when(repository.findAll()).thenReturn(Flux.just(entity));
        Mockito.when(mapper.toModel(entity)).thenReturn(type);

        StepVerifier.create(adapter.findAll())
            .expectNextMatches(t -> t.getId().equals(1L) && t.getName().equals("DNI"))
            .verifyComplete();
    }

    @Test
    void findByName_shouldReturnTypeIdentificationIfExists() {
        TypeIdentificationEntity entity = new TypeIdentificationEntity();
        TypeIdentification type = new TypeIdentification.Builder().id(1L).name("DNI").build();
        Mockito.when(repository.findByName("DNI")).thenReturn(Mono.just(entity));
        Mockito.when(mapper.toModel(entity)).thenReturn(type);

        StepVerifier.create(adapter.findByName("DNI"))
            .expectNextMatches(t -> t.getId().equals(1L) && t.getName().equals("DNI"))
            .verifyComplete();
    }

    @Test
    void findByName_shouldReturnEmptyIfNotExists() {
        Mockito.when(repository.findByName("DNI")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findByName("DNI"))
            .expectComplete()
            .verify();
    }
}