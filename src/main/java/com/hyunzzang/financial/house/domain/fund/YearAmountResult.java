package com.hyunzzang.financial.house.domain.fund;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Year;

@Getter
@NoArgsConstructor
public class YearAmountResult {
    private int year;
    private long amount;

    public YearAmountResult(Year year, Double amount) {
        this.year = year.getValue();
        this.amount = amount.longValue();
    }
}
