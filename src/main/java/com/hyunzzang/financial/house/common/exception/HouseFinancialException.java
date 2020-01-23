package com.hyunzzang.financial.house.common.exception;

import lombok.Getter;

public class HouseFinancialException extends RuntimeException {

    @Getter
    private HouseFinancialErrorMessage houseFinancialErrorMessage;

    public HouseFinancialException(HouseFinancialErrorMessage houseFinancialErrorMessage) {
        super(houseFinancialErrorMessage.getMessage());
        this.houseFinancialErrorMessage = houseFinancialErrorMessage;
    }
}
