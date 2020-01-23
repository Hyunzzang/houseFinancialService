package com.hyunzzang.financial.house.domain.auth;

public interface TokenGenerator {

    String generate(Account account);
}
