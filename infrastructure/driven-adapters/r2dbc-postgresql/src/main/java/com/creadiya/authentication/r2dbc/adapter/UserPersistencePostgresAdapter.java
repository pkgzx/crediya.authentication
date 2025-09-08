package com.creadiya.authentication.r2dbc.adapter;

import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.user.spi.IUserRepository;
import com.creadiya.authentication.r2dbc.entity.UserEntity;
import com.creadiya.authentication.r2dbc.mapper.IUserPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IUserPostgresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class UserPersistencePostgresAdapter implements IUserRepository {
  private final IUserPostgresRepository userPostgresRepository;
  private final IUserPersistenceMapper userMapper;
  private final TransactionalOperator transactionalOperator;


  @Override
  public Mono<User> save(User user) {
    UserEntity entity = userMapper.toEntity(user);
    log.info("Saving user with id: {}", entity.getId());
    return userPostgresRepository.save(entity)
      .doOnNext(e -> log.info("User saved with id: {}", e.getId()))
      .map(userMapper::toModel)
      .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<User> findByUsername(String username) {
    return userPostgresRepository.findByEmail(username)
      .map(userMapper::toModel)
      .switchIfEmpty(Mono.empty())
      .as(transactionalOperator::transactional);
  }

  @Override
  public Flux<User> findAll() {
    return userPostgresRepository.findAll()
      .map(userMapper::toModel);
  }

  @Override
  public Mono<User> findByIdentification(String identification) {
    return userPostgresRepository.findByIdentification(identification)
      .map(userMapper::toModel)
      .switchIfEmpty(Mono.empty())
      .as(transactionalOperator::transactional);
  }

  @Override
  public Mono<User> findByPhone(String phone) {
    return userPostgresRepository.findByPhone(phone)
      .map(userMapper::toModel)
      .switchIfEmpty(Mono.empty())
      .as(transactionalOperator::transactional);
  }

  @Override
  public Mono<User> findById(String id) {
    return userPostgresRepository.findById(id)
      .map(userMapper::toModel)
      .switchIfEmpty(Mono.empty());
  }
}
