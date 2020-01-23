package com.hyunzzang.financial.house.common.exception;

import lombok.Getter;

@Getter
public enum HouseFinancialErrorMessage {
    UNKNOWN_ERROR("알수 없는 에러."),
    CSV_READER_ERROR("CSV 읽기 에러.");

    String message;

    HouseFinancialErrorMessage(String message) {
        this.message = message;
    }

}
