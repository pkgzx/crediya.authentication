package com.creadiya.authentication.config;


import com.creadiya.authentication.model.permission.spi.IPermissionRepository;
import com.creadiya.authentication.r2dbc.adapter.PermissionPostgresPersistenceAdapter;

import com.creadiya.authentication.r2dbc.mapper.IPermissionPersistenceMapper;
import com.creadiya.authentication.r2dbc.repository.IPermissionPostgresRepository;
import com.creadiya.authentication.usecase.permission.PermissionUseCase;
import com.creadiya.authentication.usecase.permission.api.IPermissionServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
  private final IPermissionPostgresRepository permissionPostgresRepository;
  private final IPermissionPersistenceMapper permissionMapper;

  @Bean
  public PermissionPostgresPersistenceAdapter permissionRepository() {
    return new PermissionPostgresPersistenceAdapter(permissionPostgresRepository, permissionMapper);
  }


  @Bean
  public IPermissionServicePort permissionServicePort(IPermissionRepository repository) {
    return new PermissionUseCase(repository);
  }
}
