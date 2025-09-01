package com.creadiya.user.usecase.user;

import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.user.spi.IUserRepository;
import com.creadiya.authentication.usecase.permission.api.ICurrencyConversionServicePort;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.usecase.permission.cases.UserUseCase;
import com.creadiya.authentication.usecase.permission.enums.TechnicalMessage;
import com.creadiya.authentication.usecase.permission.exceptions.BusinessException;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    IUserRepository userRepository;
    IRoleServicePort roleServicePort;
    ITypeIdentificationServicePort typeIdentificationServicePort;
    ICurrencyConversionServicePort currencyConversion;
    UserUseCase useCase;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        roleServicePort = mock(IRoleServicePort.class);
        typeIdentificationServicePort = mock(ITypeIdentificationServicePort.class);
        currencyConversion = mock(ICurrencyConversionServicePort.class);
        useCase = new UserUseCase(userRepository, roleServicePort, typeIdentificationServicePort, currencyConversion);
    }

    User buildUser() {
        Role role = Role.builder().id(1L).build();
        TypeIdentification typeId = TypeIdentification.builder().id(1L).build();
        BaseSalary baseSalary = BaseSalary.builder()
                .value(BigDecimal.valueOf(2_000_000))
                .currency(
                  Currency.getInstance("COP")
                )
                .build();
        return User.builder()
                .email("user@mail.com")
                .identification("123")
                .phone("321")
                .birthday(LocalDate.of(1990, 1, 1))
                .baseSalary(baseSalary)
                .role(role)
                .typeIdentification(typeId)
                .build();
    }

    @Test
    void createUser_success() {
        User user = buildUser();
        when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(2_000_000)));
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
        when(roleServicePort.getRoleById(anyLong())).thenReturn(Mono.just(user.getRole()));
        when(typeIdentificationServicePort.getTypeIdentificationById(anyInt())).thenReturn(Mono.just(user.getTypeIdentification()));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.createUser(user))
                .expectNextMatches(u -> u.getEmail().equals("user@mail.com"))
                .verifyComplete();
    }

    @Test
    void getUserByUsername_found() {
        User user = buildUser();
        when(userRepository.findByUsername("user")).thenReturn(Mono.just(user));
        StepVerifier.create(useCase.getUserByUsername("user"))
                .expectNext(user)
                .verifyComplete();
    }

  @Test
  void createUser_baseSalaryOutOfRange() {
      User user = buildUser();
      // Simula un salario convertido menor al mínimo permitido
      when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(2300000000000.23)));
      when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
      when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
      when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
      when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
      // El save no se ejecuta porque el flujo falla antes
      StepVerifier.create(useCase.createUser(user))
              .expectErrorMatches(e -> e instanceof BusinessException &&
                      ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.USER_BASE_SALARY_OUT_OF_RANGE)
              .verify();
  }

    @Test
    void getUserByUsername_notFound() {
        when(userRepository.findByUsername("user")).thenReturn(Mono.empty());
        StepVerifier.create(useCase.getUserByUsername("user"))
                .verifyComplete();
    }

    @Test
    void createUser_emailAlreadyExists() {
        User user = buildUser();
        when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(2_000_000)));
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.just(user));
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.createUser(user))
            .expectErrorMatches(e -> e instanceof BusinessException &&
                ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.USER_EMAIL_ALREADY_EXISTS)
            .verify();
    }

    @Test
    void createUser_identificationAlreadyExists() {
        User user = buildUser();
        when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(2_000_000)));
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.just(user));
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.createUser(user))
            .expectErrorMatches(e -> e instanceof BusinessException &&
                ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.USER_IDENTIFICATION_ALREADY_EXISTS)
            .verify();
    }

    @Test
    void createUser_phoneAlreadyExists() {
        User user = buildUser();
        when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(2_000_000)));
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.just(user));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.createUser(user))
            .expectErrorMatches(e -> e instanceof BusinessException &&
                ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.USER_PHONE_ALREADY_EXISTS)
            .verify();
    }

    @Test
    void getAllUsers_found() {
        User user1 = buildUser();
        User user2 = buildUser();
        user2.setEmail("user2");
        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));
        StepVerifier.create(useCase.getAllUsers())
                .expectNext(user1)
                .expectNext(user2)
                .verifyComplete();
    }

    @Test
    void getAllUsers_empty() {
        when(userRepository.findAll()).thenReturn(Flux.empty());
        StepVerifier.create(useCase.getAllUsers())
                .verifyComplete();
    }

    @Test
    void checkUserExists_allNotExist() {
        User user = buildUser();
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
        StepVerifier.create(useCase.checkUserExists(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void checkOtherEntities_success() {
        User user = buildUser();
        when(roleServicePort.getRoleById(anyLong())).thenReturn(Mono.just(user.getRole()));
        when(typeIdentificationServicePort.getTypeIdentificationById(anyInt())).thenReturn(Mono.just(user.getTypeIdentification()));
        StepVerifier.create(useCase.checkOtherEntities(user))
                .expectNext(user)
                .verifyComplete();
    }


    @Test
    void createUser_baseSalaryBelowMinimum() {
        User user = buildUser();
        // Simula un salario convertido menor al mínimo permitido
        when(currencyConversion.convert(any(), any())).thenReturn(Mono.just(BigDecimal.valueOf(-2000_000)));
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByIdentification(anyString())).thenReturn(Mono.empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(typeIdentificationServicePort.getTypeIdentificationById(anyInt()))
        .thenReturn(Mono.just(user.getTypeIdentification()));
        when(roleServicePort.getRoleById(anyLong())).thenReturn(Mono.just(user.getRole()));

        StepVerifier.create(useCase.createUser(user))
            .expectErrorMatches(e -> e instanceof BusinessException &&
                ((BusinessException) e).getTechnicalMessage() == TechnicalMessage.USER_BASE_SALARY_OUT_OF_RANGE)
            .verify();
    }
}