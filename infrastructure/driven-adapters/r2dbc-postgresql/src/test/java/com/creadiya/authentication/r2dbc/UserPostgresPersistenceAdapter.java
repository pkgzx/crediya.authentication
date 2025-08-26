package com.creadiya.authentication.r2dbc;

import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.r2dbc.adapter.UserPersistencePostgresAdapter;
import com.creadiya.authentication.r2dbc.entity.UserEntity;
import com.creadiya.authentication.r2dbc.mapper.IUserPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IUserPostgresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

class UserPostgresPersistenceAdapterTest {

  private IUserPostgresRepository userRepo;
  private IUserPersistenceMapper userMapper;
  private TransactionalOperator transactionalOperator;
  private UserPersistencePostgresAdapter adapter;

  @BeforeEach
  void setUp() {
    userRepo = Mockito.mock(IUserPostgresRepository.class);
    userMapper = Mockito.mock(IUserPersistenceMapper.class);
    transactionalOperator = Mockito.mock(TransactionalOperator.class);

    Mockito.when(transactionalOperator.transactional(Mockito.any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
    Mockito.when(transactionalOperator.transactional(Mockito.any(Flux.class))).thenAnswer(invocation -> invocation.getArgument(0));

    adapter = new UserPersistencePostgresAdapter(userRepo, userMapper, transactionalOperator);
  }

  @Test
  void save_shouldReturnSavedUser() {
    User user = new User.Builder().id(null).email("test@example.com").build();
    UserEntity entity = new UserEntity();
    User savedUser = new User.Builder().id(UUID.randomUUID()).email("test@example.com").build();

    Mockito.when(userMapper.toEntity(user)).thenReturn(entity);
    Mockito.when(userRepo.save(entity)).thenReturn(Mono.just(entity));
    Mockito.when(userMapper.toModel(entity)).thenReturn(savedUser);

    StepVerifier.create(adapter.save(user))
      .expectNext(savedUser)
      .verifyComplete();
  }

  @Test
  void findByUsername_shouldReturnUser() {
    UserEntity entity = new UserEntity();
    User user = new User.Builder().id(UUID.randomUUID()).email("test@example.com").build();

    Mockito.when(userRepo.findByEmail("test@example.com")).thenReturn(Mono.just(entity));
    Mockito.when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByUsername("test@example.com"))
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void findAll_shouldReturnAllUsers() {
    UserEntity entity = new UserEntity();
    User user = new User.Builder().id(UUID.randomUUID()).email("test@example.com").build();

    Mockito.when(userRepo.findAll()).thenReturn(Flux.just(entity));
    Mockito.when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findAll())
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void findByIdentification_shouldReturnUser() {
    UserEntity entity = new UserEntity();
    User user = new User.Builder().id(UUID.randomUUID()).identification("123456").build();

    Mockito.when(userRepo.findByIdentification("123456")).thenReturn(Mono.just(entity));
    Mockito.when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByIdentification("123456"))
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void findByPhone_shouldReturnUser() {
    UserEntity entity = new UserEntity();
    User user = new User.Builder().id(UUID.randomUUID()).phone("1234567890").build();

    Mockito.when(userRepo.findByPhone("1234567890")).thenReturn(Mono.just(entity));
    Mockito.when(userMapper.toModel(entity)).thenReturn(user);

    StepVerifier.create(adapter.findByPhone("1234567890"))
      .expectNext(user)
      .verifyComplete();
  }
}