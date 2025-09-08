package com.creadiya.authentication.usecase.permission.cases;

import com.creadiya.authentication.model.auth.gateway.IPasswordEncoder;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.user.spi.IUserRepository;
import com.creadiya.authentication.usecase.permission.api.ICurrencyConversionServicePort;
import com.creadiya.authentication.usecase.permission.api.IRoleServicePort;
import com.creadiya.authentication.usecase.permission.api.ITypeIdentificationServicePort;
import com.creadiya.authentication.model.enums.MoneyConstants;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import com.creadiya.authentication.usecase.permission.validation.UserValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class UserUseCase  {
  private final IUserRepository userRepository;
  private final IRoleServicePort roleServicePort;
  private final ITypeIdentificationServicePort typeIdentificationServicePort;
  private final ICurrencyConversionServicePort currencyConversion;
  private final IPasswordEncoder passwordEncoder;

  public UserUseCase(IUserRepository userRepository, IRoleServicePort roleServicePort,
                     ITypeIdentificationServicePort typeIdentificationServicePort, ICurrencyConversionServicePort currencyConversion, IPasswordEncoder passwordEncoder) {
    this.currencyConversion = currencyConversion;
    this.roleServicePort = roleServicePort;
    this.typeIdentificationServicePort = typeIdentificationServicePort;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Mono<User> createUser(User user) {
    return
        UserValidator.validateMinimumAge(user.getBirthday())
        .then(UserValidator.validateBaseSalaryValue(user.getBaseSalary().getValue()))
        .then(checkBaseSalary(user))
        .then(Mono.defer(() -> checkUserExists(user)))
        .then(Mono.defer(() -> checkOtherEntities(user)))
          .flatMap(userModel -> passwordEncoder.encode(user.getPassword())
            .flatMap(hash -> {
              userModel.setPassword(hash);
              return userRepository.save(userModel);
            })
          )
      .flatMap(savedUser -> roleServicePort.getRoleById(user.getRole().getId())
        .flatMap(role -> {
          savedUser.setRole(role);
          return typeIdentificationServicePort.getTypeIdentificationById(Math.toIntExact(user.getTypeIdentification().getId()))
            .flatMap(typeIdentification -> {
              savedUser.setTypeIdentification(typeIdentification);
              return Mono.just(savedUser);
            });
        })
      );
  }

  public Mono<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Mono<User> getUserById(String id) {
    return userRepository.findById(id);
  }

  public Flux<User> getAllUsers() {
    return userRepository.findAll();
  }

  private Mono<User> checkBaseSalary(User user) {
    return currencyConversion.convert(user.getBaseSalary().getValue(),
      user.getBaseSalary().getCurrency())
      .flatMap(valueLocalCurrency -> {
        if (valueLocalCurrency.compareTo(MoneyConstants.MIN_COP_BASE_SALARY.getValue()) < 0 ||
          valueLocalCurrency.compareTo(MoneyConstants.MAX_COP_BASE_SALARY.getValue()) > 0) {
          return Mono.error(new BusinessException(TechnicalMessage.USER_BASE_SALARY_OUT_OF_RANGE));
        } else {
          return Mono.just(user);
        }
      });
  }

  public Mono<User> checkUserExists(User user) {
    return userRepository.findByUsername(user.getEmail())
      .flatMap(exist ->Mono.error(new BusinessException(TechnicalMessage.USER_EMAIL_ALREADY_EXISTS))
      )
      .switchIfEmpty(Mono.just(user))
      .flatMap(u -> userRepository.findByIdentification(user.getIdentification())
        .flatMap(exist ->  Mono.error(new BusinessException(TechnicalMessage.USER_IDENTIFICATION_ALREADY_EXISTS))
        )
        .switchIfEmpty(Mono.just(user))
      )
      .flatMap(u -> userRepository.findByPhone(user.getPhone())
        .flatMap(exist -> Mono.<User>error(new BusinessException(TechnicalMessage.USER_PHONE_ALREADY_EXISTS))
        )
        .switchIfEmpty(Mono.just(user))
      );
  }

  public Mono<User> checkOtherEntities(User user) {

    return roleServicePort.getRoleById(user.getRole().getId())
      .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.ROLE_NOT_FOUND)))
      .then(typeIdentificationServicePort.getTypeIdentificationById(Math.toIntExact(user.getTypeIdentification().getId()))
        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.TYPE_IDENTIFICATION_NOT_FOUND))))
      .then(Mono.just(user));
  }
}
