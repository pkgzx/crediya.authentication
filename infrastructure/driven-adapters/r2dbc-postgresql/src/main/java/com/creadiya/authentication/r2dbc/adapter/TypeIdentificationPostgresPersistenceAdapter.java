package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.r2dbc.mapper.ITypeIdentificationPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.ITypeIdentificationPostgresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class TypeIdentificationPostgresPersistenceAdapter implements ITypeIdentificationRepository {
    private final ITypeIdentificationPostgresRepository repository;
    private final ITypeIdentificationPersistenceMapper mapper;
    private final TransactionalOperator transactionalOperator;

  @Override
  public Mono<TypeIdentification> save(TypeIdentification typeIdentifaction) {
    return repository.save(mapper.toEntity(typeIdentifaction))
      .doOnNext(saved -> log.info("TypeIdentification saved with id: {}", saved.getId()))
      .map(mapper::toModel)
      .as(transactionalOperator::transactional);
  }

  @Override
  public Flux<TypeIdentification> findAll() {
    return repository.findAll()
      .doOnNext(saved -> log.info("TypeIdentification found with id: " + saved.getId()))
      .map(mapper::toModel);
  }

  @Override
  public Mono<TypeIdentification> findByName(String name) {
    return repository.findByName(name)
      .map(mapper::toModel);
  }

  @Override
  public Mono<TypeIdentification> findById(Integer id) {
    return repository.findById(id)
      .map(mapper::toModel)
      .as(transactionalOperator::transactional);
  }
}
