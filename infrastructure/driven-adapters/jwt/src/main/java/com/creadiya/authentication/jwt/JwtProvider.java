package com.creadiya.authentication.jwt;

import com.creadiya.authentication.model.auth.gateway.IAuthProvider;
import com.creadiya.authentication.model.enums.TechnicalMessage;
import com.creadiya.authentication.model.exceptions.BusinessException;
import com.creadiya.authentication.model.user.User;
import com.creadiya.authentication.model.auth.Auth;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtProvider implements IAuthProvider {

  private static final Logger LOGGER =  Logger.getLogger(JwtProvider.class.getName());

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Integer expiration;

  public Claims getClaims(String token) {
    return Jwts.parser()
      .verifyWith(getKey(secret))
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }




  private SecretKey getKey(String secret) {
    byte[] secretBytes = Decoders.BASE64URL.decode(secret);
    return Keys.hmacShaKeyFor(secretBytes);
  }

  @Override
  public Mono<Auth> generateToken(User user) {
    String token = Jwts.builder()
      .subject(user.getEmail())
      .claim("role", user.getRole().getName())
      .claim("documentId", user.getIdentification())
      .issuedAt(new Date())
      .expiration(new Date(new Date().getTime() + (expiration * 1000L)))
      .signWith(getKey(secret))
      .compact();
    return Mono.just(new Auth(token));
  }

  @Override
  public Mono<Boolean> validateToken(String token) {
    return Mono.fromSupplier(() -> {
        String subject = Jwts.parser()
          .verifyWith(getKey(secret))
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject();
        return !Objects.isEmpty(subject);
      })
      .onErrorResume(exception -> Mono.error(new BusinessException(TechnicalMessage.INVALID_TOKEN)));
  }

  @Override
  public Mono<String> getSubject(String token) {
    return Mono.just(Jwts.parser()
      .verifyWith(getKey(secret))
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject());
  }
}