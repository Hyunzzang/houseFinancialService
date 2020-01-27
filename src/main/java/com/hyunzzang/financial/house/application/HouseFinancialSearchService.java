package com.hyunzzang.financial.house.application;

import com.hyunzzang.financial.house.common.dto.BankAverageResponse;
import com.hyunzzang.financial.house.common.dto.EstimateAmountResponse;
import com.hyunzzang.financial.house.common.exception.HouseFinancialErrorMessage;
import com.hyunzzang.financial.house.common.exception.HouseFinancialException;
import com.hyunzzang.financial.house.domain.fund.HouseFund;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import com.hyunzzang.financial.house.domain.fund.YearAmountResult;
import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HouseFinancialSearchService {

  private HouseFundService houseFundService;
  private InstitutionService institutionService;


  @Autowired
  public HouseFinancialSearchService(HouseFundService houseFundService,
      InstitutionService institutionService) {
    this.houseFundService = houseFundService;
    this.institutionService = institutionService;
  }


  public BankAverageResponse getMaxMinAvgAmountForBank(String bankName) {
    Institution institution = institutionService.getInstitutionByName(bankName);
    if (institution == null) {
      throw new HouseFinancialException(HouseFinancialErrorMessage.INSTITUTION_NONE);
    }

    List<YearAmountResult> avgAmountResultList = houseFundService.getMaxMinAvgAmount(institution);

    return new BankAverageResponse(bankName, avgAmountResultList);
  }

  public EstimateAmountResponse getEstimateAmount(String bankName, int month) {
    Institution institution = institutionService.getInstitutionByName(bankName);
    if (institution == null) {
      throw new HouseFinancialException(HouseFinancialErrorMessage.INSTITUTION_NONE);
    }

    List<HouseFund> amountResultList = houseFundService.getAmountList(institution);

    return new EstimateAmountResponse();
  }
}