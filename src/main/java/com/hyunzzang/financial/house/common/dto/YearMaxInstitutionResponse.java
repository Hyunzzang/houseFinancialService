package com.hyunzzang.financial.house.common.dto;

import com.hyunzzang.financial.house.domain.fund.YearSumAmountResult;
import lombok.Getter;

@Getter
public class YearMaxInstitutionResponse {
    private int year;
    private String bank;

    public YearMaxInstitutionResponse(YearSumAmountResult yearSumAmountResult) {
        this.year = yearSumAmountResult.getYear().getValue();
        this.bank = yearSumAmountResult.getInstitution().getName();
    }
}
