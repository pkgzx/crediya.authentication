package com.creadiya.user.usecase.user;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.user.spi.IUserRepository;
import com.creadiya.authentication.usecase.permission.api.ICurrencyConversionServicePort;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.usecase.permission.cases.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

class UserUseCaseTest {

  private IUserRepository userRepository;
  private IRoleServicePort roleServicePort;
  private ITypeIdentificationServicePort typeIdentificationServicePort;
  private ICurrencyConversionServicePort currencyConversionServicePort;
  private UserUseCase userUseCase;

  @BeforeEach
  void setUp() {
    userRepository = Mockito.mock(IUserRepository.class);
    roleServicePort = Mockito.mock(IRoleServicePort.class);
    typeIdentificationServicePort = Mockito.mock(ITypeIdentificationServicePort.class);
    currencyConversionServicePort = Mockito.mock(ICurrencyConversionServicePort.class);
    userUseCase = new UserUseCase(userRepository, roleServicePort, typeIdentificationServicePort, currencyConversionServicePort);
  }

@Test
void createUser_shouldReturnSavedUser() {
  Role role = new Role.Builder().id(1L).name("Admin").build();
  TypeIdentification typeIdentification = new TypeIdentification.Builder().id(1L).name("DNI").build();
  User user = new User.Builder()
    .email("test@example.com")
    .name("John")
    .lastName("Doe")
    .phone("1234567890")
    .password("Password@123")
    .address("address")
    .birthday(LocalDate.of(1990, 1, 1))
    .identification("123456")
    .role(role)
    .typeIdentification(typeIdentification)
    .baseSalary(new BaseSalary(Currency.getInstance("USD"), BigDecimal.valueOf(1000)))
    .build();

  Mockito.when(userRepository.findByUsername(user.getEmail())).thenReturn(Mono.empty());
  Mockito.when(userRepository.findByIdentification(user.getIdentification())).thenReturn(Mono.empty());
  Mockito.when(userRepository.findByPhone(user.getPhone())).thenReturn(Mono.empty());
  Mockito.when(userRepository.save(user)).thenReturn(Mono.just(user));
  Mockito.when(roleServicePort.getRoleById(role.getId())).thenReturn(Mono.just(role));
  Mockito.when(typeIdentificationServicePort.getTypeIdentificationById(Math.toIntExact(typeIdentification.getId())))
         .thenReturn(Mono.just(typeIdentification));
  Mockito.when(currencyConversionServicePort.convert(Mockito.any(BigDecimal.class), Mockito.any(Currency.class)))
         .thenReturn(Mono.just(BigDecimal.valueOf(1000)));

  StepVerifier.create(userUseCase.createUser(user))
    .expectNext(user)
    .verifyComplete();
}
  @Test
  void getUserByUsername_shouldReturnUser() {
    User user = new User.Builder().email("test@example.com").build();

    Mockito.when(userRepository.findByUsername("test@example.com")).thenReturn(Mono.just(user));

    StepVerifier.create(userUseCase.getUserByUsername("test@example.com"))
      .expectNext(user)
      .verifyComplete();
  }

  @Test
  void getAllUsers_shouldReturnAllUsers() {
    User user1 = new User.Builder().email("user1@example.com").build();
    User user2 = new User.Builder().email("user2@example.com").build();

    Mockito.when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

    StepVerifier.create(userUseCase.getAllUsers())
      .expectNext(user1)
      .expectNext(user2)
      .verifyComplete();
  }
}