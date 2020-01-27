package com.hyunzzang.financial.house.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EstimateAmountResponse {

  private String bank;
  private int year;
  private int month;
  private long amount;
}
