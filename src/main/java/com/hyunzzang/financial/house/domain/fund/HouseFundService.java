package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        return houseFundRepository.saveAll(houseFunds);
    }

    public List<YearTotalAmountResponse> getAllYearSumAmount() {
        List<YearSumAmountResult> resList = houseFundRepository.getYearSumAmount();
        Map<Year, List<YearSumAmountResult>> yearGroupMap = resList.stream()
                .collect(groupingBy(YearSumAmountResult::getYear));

        return yearGroupMap.keySet().stream()
                .map(k -> new YearTotalAmountResponse(k, yearGroupMap.get(k)))
                .collect(Collectors.toList());
    }
}
