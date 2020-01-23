package com.hyunzzang.financial.house.domain.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseFundRepository extends JpaRepository<HouseFund, Long> {

    @Query("SELECT new com.hyunzzang.financial.house.domain.fund.YearSumAmountResult" +
            "(h.year, h.institution , SUM(h.amount) as sumAmount) " +
            "FROM HouseFund h group by h.year, h.institution")
    List<YearSumAmountResult> getYearSumAmount();
}
