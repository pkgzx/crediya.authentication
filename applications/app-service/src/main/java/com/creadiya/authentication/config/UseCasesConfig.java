package com.creadiya.authentication.config;


import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.model.role.spi.IRoleRepository;
import com.creadiya.authentication.model.typeIdentification.spi.ITypeIdentificationRepository;
import com.creadiya.authentication.model.user.spi.IUserRepository;
import com.creadiya.authentication.r2dbc.adapter.PermissionPostgresPersistenceAdapter;

import com.creadiya.authentication.r2dbc.adapter.RolePostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.adapter.TypeIdentificationPostgresPersistenceAdapter;
import com.creadiya.authentication.r2dbc.adapter.UserPersistencePostgresAdapter;
import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.mapper.IRolePersistenceMapper;
import com.creadiya.authentication.r2dbc.mapper.ITypeIdentificationPersistenceMapper;
import com.creadiya.authentication.r2dbc.mapper.IUserPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.*;
import com.creadiya.authentication.usecase.permission.api.*;
import com.creadiya.authentication.usecase.permission.cases.PermissionUseCase;
import com.creadiya.authentication.usecase.permission.cases.RoleUseCase;
import com.creadiya.authentication.usecase.permission.cases.TypeIdentificationUseCase;
import com.creadiya.authentication.usecase.permission.cases.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
  private final IPermissionPostgresRepository permissionPostgresRepository;
  private final IPermissionPersistenceMapper permissionMapper;

  private final IRolePostgresRepository rolePostgresRepository;
  private final IRolePermissionPostgresRepository rolePermissionPostgresRepository;
  private final IRolePersistenceMapper roleMapper;

  private final ITypeIdentificationPostgresRepository typeIdentificationPostgresRepository;
  private final ITypeIdentificationPersistenceMapper typeIdentificationMapper;

  private final IUserPostgresRepository userPostgresRepository;
  private final IUserPersistenceMapper userMapper;

  private final ICurrencyConversionServicePort currencyConversionServicePort;


  @Bean
  public PermissionPostgresPersistenceAdapter permissionRepository(TransactionalOperator operator) {
    return new PermissionPostgresPersistenceAdapter(permissionPostgresRepository, permissionMapper, operator);
  }


  @Bean
  public IPermissionServicePort permissionServicePort(IPermissionRepository repository) {
    return new PermissionUseCase(repository);
  }



  @Bean
  public RolePostgresPersistenceAdapter roleRepository(PermissionPostgresPersistenceAdapter permissionAdapter, TransactionalOperator operator) {
    return new RolePostgresPersistenceAdapter(rolePostgresRepository, rolePermissionPostgresRepository,
      roleMapper, permissionAdapter,  operator);
  }

  @Bean
  public IRoleServicePort roleServicePort(IRoleRepository repository, IPermissionServicePort permissionServicePort) {
    return new RoleUseCase(repository, permissionServicePort);
  }


  @Bean
  public TypeIdentificationPostgresPersistenceAdapter typeIdentificationRepository(TransactionalOperator operator) {
    return new TypeIdentificationPostgresPersistenceAdapter(typeIdentificationPostgresRepository, typeIdentificationMapper, operator);
  }
  @Bean
  public ITypeIdentificationServicePort typeIdentificationServicePort(ITypeIdentificationRepository repository) {
    return new TypeIdentificationUseCase(repository);
  }


  @Bean
  public UserPersistencePostgresAdapter userRepository(TransactionalOperator operator) {
    return new UserPersistencePostgresAdapter(userPostgresRepository, userMapper, operator);
  }

  @Bean
  public UserUseCase userServicePort(IUserRepository repository, IRoleServicePort roleServicePort, ITypeIdentificationServicePort typeIdentificationServicePort) {
    return new UserUseCase(repository, roleServicePort, typeIdentificationServicePort, currencyConversionServicePort);
  }
}
