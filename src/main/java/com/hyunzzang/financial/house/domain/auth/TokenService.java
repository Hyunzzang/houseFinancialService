package com.hyunzzang.financial.house.domain.auth;

public interface TokenService {

  String generate(Account account);

  String refresh(String token);

  boolean checkToken(String token);
}
