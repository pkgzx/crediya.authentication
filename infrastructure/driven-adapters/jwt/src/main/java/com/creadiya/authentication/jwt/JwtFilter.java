package com.creadiya.authentication.jwt;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().value();
    if(path.startsWith("/v3/api-docs") ||
      path.startsWith("/api/swagger-ui") ||
      path.startsWith("/api/webjars/swagger-ui") ||
      path.equals("/api/v1/auth/sign-in") ||
      path.equals("/api/v1/users") ||
      path.equals("/api/v1/auth/validate-token") ||
      path.equals("/api/docs") ||
      path.startsWith("/webjars/swagger-ui") ||
      path.startsWith("/api/v1/users/email/") ||
      path.startsWith("/api/v1/users/id/") ||
      path.equals("/favicon.ico"))
      return chain.filter(exchange);
    String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if(auth == null){
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    if(!auth.startsWith("Bearer ")){
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    String token = auth.replace("Bearer ", "");
    exchange.getAttributes().put("token", token);
    return chain.filter(exchange);
  }
}