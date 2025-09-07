package com.creadiya.authentication.jwt;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

  private final SecurityContextRepository securityContextRepository;


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {

    return http
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .authorizeExchange(exchange -> exchange
        .pathMatchers(
          "/v3/api-docs",
          "/api/swagger-ui.html",
          "/api/swagger-ui/",
          "/api/webjars/swagger-ui/**",
          "/api/docs",
          "/api/v1/auth/sign-in",
          "/api/v1/users",
          "/api/v1/auth/validate-token",
          "/webjars/swagger-ui/**",
          "/favicon.ico",
          "/v3/api-docs/swagger-config",
          "/api/v1/users/email/*"
        ).permitAll()
        .pathMatchers(
          "/api/v1/users"
        ).hasAnyRole("ADMIN","ADVISOR")
        .anyExchange().authenticated()
      )
      .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
      .securityContextRepository(securityContextRepository)
      .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
      .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
      .logout(ServerHttpSecurity.LogoutSpec::disable)
      .build();
  }
}