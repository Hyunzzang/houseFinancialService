package com.hyunzzang.financial.house.domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtTokenService implements TokenService {

  private static final String SECRET_KEY = "secretkey";
  private static final String CLAIM_KEY_ID = "id";

  @Override
  public String generate(Account account) {
    return generateJwt(account.getAuthId());
  }

  @Override
  public String refresh(String token) {
    final Claims claims = parserJwt(token);
    String authId = (String) claims.get(CLAIM_KEY_ID);
    return generateJwt(authId);
  }

  @Override
  public boolean checkToken(String token) {
    final Claims claims = parserJwt(token);
    String authId = (String) claims.get(CLAIM_KEY_ID);
    return StringUtils.isNotEmpty(authId);
  }

  private String generateJwt(String authId) {
    // todo 만료 시간을 설정해야 하지 않을까
    return Jwts.builder()
        .setSubject(authId)
        .claim(CLAIM_KEY_ID, "user")
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  private Claims parserJwt(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
}
