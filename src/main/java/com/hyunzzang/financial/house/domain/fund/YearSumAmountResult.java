package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.domain.institution.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Year;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearSumAmountResult {

  private Year year;
  private Institution institution;
  private Long sumAmount;
}
