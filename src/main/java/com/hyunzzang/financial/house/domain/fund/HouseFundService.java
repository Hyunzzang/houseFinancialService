package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.common.dto.YearMaxInstitutionResponse;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import com.hyunzzang.financial.house.domain.institution.Institution;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class HouseFundService {

  private HouseFundRepository houseFundRepository;

  @Autowired
  public HouseFundService(HouseFundRepository houseFundRepository) {
    this.houseFundRepository = houseFundRepository;
  }


  @Transactional
  public List<HouseFund> addAllHouseFund(Collection<HouseFund> houseFunds) {
    return houseFundRepository.saveAll(houseFunds);
  }

  @Transactional(readOnly = true)
  public List<YearTotalAmountResponse> getAllYearSumAmount() {
    // todo 데이터가 많을 경우를 대비 하자.
    List<YearSumAmountResult> resList = houseFundRepository.findYearSumAmount();
    Map<Year, List<YearSumAmountResult>> yearGroupMap = resList.stream()
        .collect(groupingBy(YearSumAmountResult::getYear));

    return yearGroupMap.keySet().stream()
        .map(k -> new YearTotalAmountResponse(k, yearGroupMap.get(k)))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public YearMaxInstitutionResponse getYearMaxAmountInstitution() {
    // todo 데이터가 많을 경우를 대비 하자.
    List<YearSumAmountResult> resList = houseFundRepository.findYearSumAmount();
    YearSumAmountResult opMaxAmountResult = resList.stream()
        .sorted(Comparator.comparing(YearSumAmountResult::getSumAmount).reversed())
        .findFirst().orElseThrow(NoSuchElementException::new);

    return new YearMaxInstitutionResponse(opMaxAmountResult);
  }

  @Transactional(readOnly = true)
  public List<YearAmountResult> getMaxMinAvgAmount(Institution institution) {
    List<YearAmountResult> yearAmountResultList = houseFundRepository
        .findYearAvgAmountByInstitution(institution);
    if (CollectionUtils.isEmpty(yearAmountResultList)) {
      return Collections.EMPTY_LIST;
    }

    YearAmountResult maxAmount = yearAmountResultList.stream()
        .max(Comparator.comparing(YearAmountResult::getAmount))
        .orElseThrow(NoSuchElementException::new);
    YearAmountResult minAmount = yearAmountResultList.stream()
        .min(Comparator.comparing(YearAmountResult::getAmount))
        .orElseThrow(NoSuchElementException::new);

    return Arrays.asList(minAmount, maxAmount);
  }

  @Transactional(readOnly = true)
  public List<HouseFund> getAmountList(Institution institution) {
    return houseFundRepository.findAllByInstitutionOrderByYearAscMonthAsc(institution);
  }

//    private Map<Year, List<YearSumAmountResult>> getYearGroupSumAmount() {
//
//    }

}
