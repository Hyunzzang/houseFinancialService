package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionCode;
import com.hyunzzang.financial.house.domain.institution.InstitutionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HouseFundRepositoryTest {

    @Autowired
    private HouseFundRepository houseFundRepository;

    @Autowired
    private InstitutionRepository institutionRepository;


    @Before
    public void setupDB() {
        Institution institution = Institution.ByInstitutionTypeBuilder()
                .institutionCode(InstitutionCode.KB_BANK)
                .build();
        institutionRepository.save(institution);

        HouseFund houseFund_1 = HouseFund.builder()
                .year(2015)
                .month(1)
                .institution(institution)
                .amount(1001L)
                .build();
        houseFundRepository.save(houseFund_1);

        HouseFund houseFund_2 = HouseFund.builder()
                .year(2015)
                .month(2)
                .institution(institution)
                .amount(1002L)
                .build();
        houseFundRepository.save(houseFund_2);
    }

    @Test
    public void findYearSumAmountTest() {
        List<YearSumAmountResult> yearSumAmountResultList = houseFundRepository.findYearSumAmount();
        assertNotNull(yearSumAmountResultList);
        YearSumAmountResult yearSumAmountResult = yearSumAmountResultList.get(0);
        assertTrue(2015 == yearSumAmountResult.getYear().getValue());
        assertTrue(2003L == yearSumAmountResult.getSumAmount());
    }

    @Test
    public void findYearAvgAmountByInstitutionTest() {
        Institution institution = institutionRepository.findByName("국민은행");
        List<YearAmountResult> yearAmountResultList = houseFundRepository.findYearAvgAmountByInstitution(institution);
        assertNotNull(yearAmountResultList);
        YearAmountResult yearAmountResult = yearAmountResultList.get(0);
        assertTrue(2015 == yearAmountResult.getYear());
        assertTrue(1002L == yearAmountResult.getAmount());
    }

    @Test
    public void findAllByInstitutionOrderByYearAscMonthAsc() {
        Institution institution = institutionRepository.findByName("국민은행");
        List<HouseFund> houseFundList = houseFundRepository.findAllByInstitutionOrderByYearAscMonthAsc(institution);
        assertNotNull(houseFundList);

        HouseFund houseFund_1 = houseFundList.get(0);
        assertTrue(2015 == houseFund_1.getYear().getValue());
        assertTrue(1 == houseFund_1.getMonth().getValue());
        assertTrue(1001L == houseFund_1.getAmount());

        HouseFund houseFund_2 = houseFundList.get(1);
        assertTrue(2015 == houseFund_2.getYear().getValue());
        assertTrue(2 == houseFund_2.getMonth().getValue());
        assertTrue(1002L == houseFund_2.getAmount());
    }
}
