package com.hyunzzang.financial.house.common.exception;

import lombok.Getter;

@Getter
public enum AccountErrorMessage {
    UNKNOWN_ERROR("알수 없는 에러."),
    TOKEN_NONE("토큰 정보가 없습니다."),
    TOKEN_INVALID("토큰이 유효하지 않습니다."),
    TOKEN_VERIFICATION_FAIL("토큰검증 실패."),
    TOKEN_REFRESH_FAIL("토큰 재발급 실패"),
    ACCOUNT_PW_ERROR("패스워드 인코딩 중 문제가 발생하였습니다."),
    ACCOUNT_NONE("계정 정보가 없습니다."),
    ACCOUNT_PW_VALIDATION_FAILED("패스워드 검증 실패."),
    ACCOUNT_DUPLICATION("등록된 ID 입니다.");


    String message;

    AccountErrorMessage(String message) {
        this.message = message;
    }
}
