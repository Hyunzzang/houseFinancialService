package com.hyunzzang.financial.house.common.exception;

import lombok.Getter;

public class HomeFinancialException extends RuntimeException {

    @Getter
    private HomeFinancialErrorMessage homeFinancialErrorMessage;

    public HomeFinancialException(HomeFinancialErrorMessage homeFinancialErrorMessage) {
        super(homeFinancialErrorMessage.getMessage());
        this.homeFinancialErrorMessage = homeFinancialErrorMessage;
    }
}
