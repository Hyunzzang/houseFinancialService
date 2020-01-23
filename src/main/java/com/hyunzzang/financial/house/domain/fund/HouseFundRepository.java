package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.domain.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Month;
import java.util.List;

public interface HouseFundRepository extends JpaRepository<HouseFund, Long> {

    @Query("SELECT new com.hyunzzang.financial.house.domain.fund.YearSumAmountResult" +
            "(h.year, h.institution , SUM(h.amount) as sumAmount) " +
            "FROM HouseFund h " +
            "group by h.year, h.institution")
    List<YearSumAmountResult> findYearSumAmount();

    /*
    SELECT  year, ROUND(avg(amount))
    FROM HOUSE_FUND
    where inst_id='KEB_BANK'
    group by year
     */
    @Query("SELECT new com.hyunzzang.financial.house.domain.fund.YearAmountResult" +
            "(h.year, ROUND(avg(h.amount)) as amount) " +
            "FROM HouseFund h where h.institution=?1 " +
            "group by h.year" )
    List<YearAmountResult> findYearAvgAmountByInstitution(Institution institution);


    List<HouseFund> findAllByInstitutionOrderByYearAscMonthAsc(Institution institution);

}
