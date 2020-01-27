package com.hyunzzang.financial.house.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hyunzzang.financial.house.domain.fund.YearSumAmountResult;
import lombok.Getter;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class YearTotalAmountResponse {

  private int year;

  @JsonProperty("total_amount")
  private long totalAmount;

  @JsonProperty("detail_amount")
  private Map<String, Long> detailAmount = new HashMap<>();


  public YearTotalAmountResponse(Year year, List<YearSumAmountResult> yearSumAmountResultList) {
    this.year = year.getValue();
    for (YearSumAmountResult val : yearSumAmountResultList) {
      if (year.getValue() != val.getYear().getValue()) {
        // todo 예외 처리를 하자.
      }
      totalAmount += val.getSumAmount();
      detailAmount.put(val.getInstitution().getName(), val.getSumAmount());
    }
  }
}
