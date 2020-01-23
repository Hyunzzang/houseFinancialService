package com.hyunzzang.financial.house.domain.fund;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearAvgAmountResult {
    private int year;
    private long amount;
}
