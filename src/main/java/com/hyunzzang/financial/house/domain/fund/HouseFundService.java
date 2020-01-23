package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.common.dto.YearMaxInstitutionResponse;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
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

    public List<HouseFund> addAllHouseFund(Collection<HouseFund> houseFunds) {
        // todo 중복 저장에 대한 예외 처리를 하자
        return houseFundRepository.saveAll(houseFunds);
    }

    @Transactional(readOnly = true)
    public List<YearTotalAmountResponse> getAllYearSumAmount() {
        Map<Year, List<YearSumAmountResult>> yearGroupMap = getYearGroupSumAmount();

        return yearGroupMap.keySet().stream()
                .map(k -> new YearTotalAmountResponse(k, yearGroupMap.get(k)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<YearMaxInstitutionResponse> getYearMaxAmountInstitution() {
        Map<Year, List<YearSumAmountResult>> yearGroupMap = getYearGroupSumAmount();

        List<YearMaxInstitutionResponse> resList = new ArrayList<>();
        for(Year year: yearGroupMap.keySet()) {
            List<YearSumAmountResult> sumAmountList = yearGroupMap.get(year);
            YearSumAmountResult yearSumAmountResult = sumAmountList.stream()
                    .max(Comparator.comparing(YearSumAmountResult::getSumAmount))
                    .orElseThrow(NoSuchElementException::new);
            resList.add(new YearMaxInstitutionResponse(yearSumAmountResult));
        }

        return resList;
    }

    private Map<Year, List<YearSumAmountResult>> getYearGroupSumAmount() {
        // todo 데이터가 많을 경우를 대비 하자.
        List<YearSumAmountResult> resList = houseFundRepository.findYearSumAmount();
        return resList.stream()
                .collect(groupingBy(YearSumAmountResult::getYear));
    }

}
