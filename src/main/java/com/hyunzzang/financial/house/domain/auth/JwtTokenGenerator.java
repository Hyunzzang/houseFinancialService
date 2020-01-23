package com.hyunzzang.financial.house.domain.auth;

import org.springframework.stereotype.Service;

@Service("tokenGenerator")
public class JwtTokenGenerator implements TokenGenerator {
    @Override
    public String generate(Account account) {
        return null;
    }
}
