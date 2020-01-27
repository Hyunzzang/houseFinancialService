package com.hyunzzang.financial.house.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hyunzzang.financial.house.domain.fund.YearAmountResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAverageResponse {

  private String bank;

  @JsonProperty("support_amount")
  private List<YearAmountResult> yearAmountResultList;
}
