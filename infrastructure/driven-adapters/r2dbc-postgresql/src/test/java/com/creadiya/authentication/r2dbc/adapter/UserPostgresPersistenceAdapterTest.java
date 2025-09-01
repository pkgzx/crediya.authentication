package com.creadiya.authentication.r2dbc.adapter;


import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.r2dbc.entity.UserEntity;
import com.creadiya.authentication.r2dbc.mapper.IUserPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IUserPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserPostgresPersistenceAdapterTest {

  private IUserPostgresRepository userRepo;
  private IUserPersistenceMapper userMapper;
  private TransactionalOperator transactionalOperator;
  private UserPersistencePostgresAdapter adapter;
  private final String uuid = UUID.randomUUID().toString();


  @BeforeEach
  void setUp() {
    userRepo = mock(IUserPostgresRepository.class);
    userMapper = mock(IUserPersistenceMapper.class);
    transactionalOperator = mock(TransactionalOperator.class);

    adapter = new UserPersistencePostgresAdapter(userRepo, userMapper, transactionalOperator);

    when(transactionalOperator.transactional(any(Mono.class)))
      .thenAnswer(invocation -> invocation.getArgument(0));
  }

  @Test
  void save_shouldReturnSavedUser() {
    User user = new User();
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    User savedUser = new User();
    savedUser.setId(UUID.fromString(uuid));

    when(userMapper.toEntity(user)).thenReturn(entity);
    when(userRepo.save(entity)).thenReturn(Mono.just(entity));
    when(userMapper.toModel(entity)).thenReturn(savedUser);

    StepVerifier.create(adapter.save(user))
      .expectNextMatches(u -> u.getId().equals(UUID.fromString(uuid)))
      .verifyComplete();
  }

  @Test
  void findByUsername_shouldReturnUserIfExists() {
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    User user = new User();
    user.setId(UUID.fromString(uuid));

    when(userRepo.findByEmail("test@mail.com")).thenReturn(Mono.just(entity));
    when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByUsername("test@mail.com"))
      .expectNextMatches(u -> u.getId().equals(UUID.fromString(uuid)))
      .verifyComplete();
  }

  @Test
  void findByUsername_shouldReturnEmptyIfNotExists() {
    when(userRepo.findByEmail(anyString())).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByUsername("notfound@mail.com"))
      .verifyComplete();
  }

  @Test
  void findAll_shouldReturnAllUsers() {
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    User user = new User();
    user.setId(UUID.fromString(uuid));

    when(userRepo.findAll()).thenReturn(Flux.just(entity));
    when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findAll())
      .expectNextMatches(u -> u.getId().equals(UUID.fromString(uuid)))
      .verifyComplete();
  }

  @Test
  void findByIdentification_shouldReturnUserIfExists() {
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    User user = new User();
    user.setId(UUID.fromString(uuid));

    when(userRepo.findByIdentification("123456")).thenReturn(Mono.just(entity));
    when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByIdentification("123456"))
      .expectNextMatches(u -> u.getId().equals(UUID.fromString(uuid)))
      .verifyComplete();
  }

  @Test
  void findByIdentification_shouldReturnEmptyIfNotExists() {
    when(userRepo.findByIdentification(anyString())).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByIdentification("notfound"))
      .verifyComplete();
  }

  @Test
  void findByPhone_shouldReturnUserIfExists() {
    UserEntity entity = new UserEntity();
    entity.setId(uuid);
    User user = new User();
    user.setId(UUID.fromString(uuid));

    when(userRepo.findByPhone("5551234")).thenReturn(Mono.just(entity));
    when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByPhone("5551234"))
      .expectNextMatches(u -> u.getId().equals(UUID.fromString(uuid)))
      .verifyComplete();
  }

  @Test
  void findByPhone_shouldReturnEmptyIfNotExists() {
    when(userRepo.findByPhone(anyString())).thenReturn(Mono.empty());

    StepVerifier.create(adapter.findByPhone("notfound"))
      .verifyComplete();
  }
}