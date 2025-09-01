package com.creadiya.authentication.api.handler;

import com.creadiya.authentication.api.dto.CreateBaseSalaryDto;
import com.creadiya.authentication.api.dto.CreateUserDto;
import com.creadiya.authentication.api.handlers.UserHandler;
import com.creadiya.authentication.api.mapper.IUserMapper;
import com.creadiya.authentication.api.validation.UserValidator;
import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeIdentification.TypeIdentification;
import com.creadiya.authentication.model.user.BaseSalary;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.usecase.permission.cases.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.*;

class UserHandlerTest {

    private UserUseCase userCase;
    private IUserMapper userMapper;
    private UserHandler userHandler;
    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userCase = mock(UserUseCase.class);
        userMapper = mock(IUserMapper.class);
        userValidator = mock(UserValidator.class);
        userHandler = new UserHandler(userCase, userMapper, userValidator);
    }

 @Test
 void createUser_shouldReturnCreatedResponse() {
     CreateUserDto dto = CreateUserDto.builder()
         .name("Antonio")
         .lastName("Hernandez")
         .address("CLL")
         .email("antonio@gmail.com")
         .phone("3023246222")
         .roleId(1L)
         .identification("128373636")
         .birthday("02/12/2000")
         .typeIdentificationId(1L)
         .password("password123")
         .baseSalary(CreateBaseSalaryDto.builder().currency("COP").value(BigDecimal.valueOf(2000)).build())
         .build();

     User user = User.builder()
         .name("Antonio")
         .lastName("Hernandez")
         .address("CLL")
         .email("antonio@gmail.com")
         .phone("3023246222")
         .identification("128373636")
         .typeIdentification(TypeIdentification.builder().id(1L).build())
         .role(Role.builder().id(1L).build())
         .baseSalary(BaseSalary.builder()
             .currency(Currency.getInstance("COP"))
             .value(BigDecimal.valueOf(2000))
             .build())
         .build();

     ServerRequest request = mock(ServerRequest.class);
     when(request.bodyToMono(CreateUserDto.class)).thenReturn(Mono.just(dto));
     when(userMapper.toModel(dto)).thenReturn(user);
     when(userCase.createUser(user)).thenReturn(Mono.just(user));

     when(userValidator.validName(any())).thenReturn(Mono.empty());
     when(userValidator.validLastName(any())).thenReturn(Mono.empty());
     when(userValidator.validEmail(any())).thenReturn(Mono.empty());
     when(userValidator.validPhone(any())).thenReturn(Mono.empty());
     when(userValidator.validAddress(any())).thenReturn(Mono.empty());
     when(userValidator.validIdentification(any())).thenReturn(Mono.empty());
     when(userValidator.validPassword(any())).thenReturn(Mono.empty());
     when(userValidator.validOtherEntities(any())).thenReturn(Mono.empty());
     when(userValidator.validateCurrency(any())).thenReturn(Mono.empty());

     Mono<ServerResponse> responseMono = userHandler.createUser(request);

     StepVerifier.create(responseMono)
         .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
         .verifyComplete();
 }
    @Test
    void getUserByEmail_shouldReturnOkResponse() {
        String email = "test@example.com";
        User user = new User();

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("email")).thenReturn(email);
        when(userCase.getUserByUsername(email)).thenReturn(Mono.just(user));

        Mono<ServerResponse> responseMono = userHandler.getUserByEmail(request);

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
            .verifyComplete();
    }

    @Test
    void getUserByEmail_shouldReturnNotFoundResponse() {
        String email = "notfound@example.com";

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("email")).thenReturn(email);
        when(userCase.getUserByUsername(email)).thenReturn(Mono.empty());

        Mono<ServerResponse> responseMono = userHandler.getUserByEmail(request);

        StepVerifier.create(responseMono)
            .expectNextMatches(response -> response.statusCode().equals(HttpStatus.NOT_FOUND))
            .verifyComplete();
    }
}