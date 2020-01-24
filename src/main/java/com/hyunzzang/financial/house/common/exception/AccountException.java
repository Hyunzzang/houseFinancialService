package com.hyunzzang.financial.house.common.exception;

import lombok.Getter;

@Getter
public class AccountException extends RuntimeException {
    private AccountErrorMessage accountErrorMessage;

    public AccountException(AccountErrorMessage accountErrorMessage) {
        super(accountErrorMessage.getMessage());
        this.accountErrorMessage = accountErrorMessage;
    }
}
