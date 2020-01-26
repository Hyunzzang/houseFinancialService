package com.hyunzzang.financial.house.common.constant;

public class AuthConstant {
    private AuthConstant() {
        throw new AssertionError("final class");
    }

    public static final String HEADER_KEY_AUTHORIZATION = "Authorization";
    public static final String HEADER_VAL_TOKEN_REFRESH = "Bearer";
}
