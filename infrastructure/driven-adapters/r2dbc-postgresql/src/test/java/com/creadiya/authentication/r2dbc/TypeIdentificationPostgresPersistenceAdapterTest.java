package com.creadiya.authentication.r2dbc;


import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.r2dbc.adapter.TypeIdentificationPostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.entity.TypeIdentificationEntity;
import com.creadiya.authentication.r2dbc.mapper.ITypeIdentificationPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.ITypeIdentificationPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeIdentificationPostgresPersistenceAdapterTest {

  @Mock
  private ITypeIdentificationPostgresRepository typeIdentificationPostgresRepository;
  @Mock
  private ITypeIdentificationPostgresRepository repository;
  @Mock
  private ITypeIdentificationPersistenceMapper mapper;
  @Mock
  private TransactionalOperator transactionalOperator;
  private TypeIdentificationPostgresPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {


    adapter = new TypeIdentificationPostgresPersistenceAdapter(repository, mapper, transactionalOperator);

  }

  @Test
  void save_shouldReturnSavedTypeIdentification() {
    TypeIdentification type = new TypeIdentification();
    TypeIdentificationEntity entity = new TypeIdentificationEntity();
    entity.setId(1L);
    TypeIdentification savedType = new TypeIdentification();
    savedType.setId(1L);

    when(mapper.toEntity(type)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(Mono.just(entity));
    when(mapper.toModel(entity)).thenReturn(savedType);

    StepVerifier.create(adapter.save(type))
      .expectNextMatches(t -> t.getId() == 1)
      .verifyComplete();
  }



  @Test
  void findById_shouldReturnTypeIdentificationIfExists() {
    TypeIdentificationEntity entity = new TypeIdentificationEntity();
    entity.setId(1L);
    TypeIdentification type = new TypeIdentification();
    type.setId(1L);

    when(repository.findById(1)).thenReturn(Mono.just(entity));
    when(mapper.toModel(entity)).thenReturn(type);

    StepVerifier.create(adapter.findById(1))
      .expectNextMatches(t -> t.getId() == 1)
      .verifyComplete();
  }

  @Test
  void findById_shouldReturnEmptyIfNotExists() {
    when(repository.findById(anyInt())).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findById(1))
      .verifyComplete();
  }



  @Test
  void findAll_shouldReturnAllTypeIdentifications() {
    TypeIdentificationEntity entity = new TypeIdentificationEntity();
    entity.setId(1L);
    TypeIdentification type = new TypeIdentification();
    type.setId(1L);

    when(repository.findAll()).thenReturn(Flux.just(entity));
    when(mapper.toModel(entity)).thenReturn(type);

    StepVerifier.create(adapter.findAll())
      .expectNextMatches(t -> t.getId() == 1)
      .verifyComplete();
  }

  @Test
  void findAll_shouldReturnEmptyIfNoneExists() {
    when(repository.findAll()).thenReturn(Flux.empty());

    StepVerifier.create(adapter.findAll())
      .verifyComplete();
  }

  @Test
  void findByName_shouldReturnTypeIdentificationIfExists() {
    TypeIdentificationEntity entity = new TypeIdentificationEntity();
    entity.setId(2L);
    TypeIdentification type = new TypeIdentification();
    type.setId(2L);

    when(repository.findByName("DNI")).thenReturn(Mono.just(entity));
    when(mapper.toModel(entity)).thenReturn(type);

    StepVerifier.create(adapter.findByName("DNI"))
      .expectNextMatches(t -> t.getId() == 2)
      .verifyComplete();
  }

  @Test
  void findByName_shouldReturnEmptyIfNotExists() {
    when(repository.findByName(anyString())).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByName("DNI"))
      .verifyComplete();
  }
}