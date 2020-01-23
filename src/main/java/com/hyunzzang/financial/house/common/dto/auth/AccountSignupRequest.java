package com.hyunzzang.financial.house.common.dto.auth;

import lombok.Getter;

@Getter
public class AccountSignupRequest {
    private String authId;
    private String pw;
}
