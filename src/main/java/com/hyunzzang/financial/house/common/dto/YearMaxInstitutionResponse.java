package com.hyunzzang.financial.house.common.dto;

import com.hyunzzang.financial.house.domain.fund.YearSumAmountResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearMaxInstitutionResponse {

  private int year;
  private String bank;

  public YearMaxInstitutionResponse(YearSumAmountResult yearSumAmountResult) {
    this.year = yearSumAmountResult.getYear().getValue();
    this.bank = yearSumAmountResult.getInstitution().getName();
  }
}
