package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.common.dto.YearMaxInstitutionResponse;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionCode;
import com.hyunzzang.financial.house.domain.institution.InstitutionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HouseFundServiceTest {

  @Autowired
  private HouseFundRepository houseFundRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private HouseFundService houseFundService;


  @Before
  public void setupDb() {
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
    HouseFund houseFund_2 = HouseFund.builder()
        .year(2015)
        .month(2)
        .institution(institution)
        .amount(1002L)
        .build();
    List<HouseFund> houseFundList = new ArrayList<>();
    houseFundList.add(houseFund_1);
    houseFundList.add(houseFund_2);
    houseFundService.addAllHouseFund(houseFundList);
  }

  @Test
  public void getAllYearSumAmountTest() {
    List<YearTotalAmountResponse> yearTotalAmountResponseList = houseFundService
        .getAllYearSumAmount();
    assertNotNull(yearTotalAmountResponseList);

    YearTotalAmountResponse yearTotalAmountResponse = yearTotalAmountResponseList.get(0);
    assertTrue(2015 == yearTotalAmountResponse.getYear());
    assertTrue(2003L == yearTotalAmountResponse.getTotalAmount());
    assertTrue(2003L == yearTotalAmountResponse.getDetailAmount().get("국민은행"));
  }

  @Test
  public void getYearMaxAmountInstitutionTest() {
    YearMaxInstitutionResponse yearMaxInstitutionResponse = houseFundService
        .getYearMaxAmountInstitution();
    assertNotNull(yearMaxInstitutionResponse);

    assertTrue(2015 == yearMaxInstitutionResponse.getYear());
    assertEquals("국민은행", yearMaxInstitutionResponse.getBank());
  }

  @Test
  public void getMaxMinAvgAmountTest() {
    Institution institution = institutionRepository.findByName("국민은행");
    List<YearAmountResult> yearAmountResultList = houseFundService.getMaxMinAvgAmount(institution);
    assertNotNull(yearAmountResultList);

    YearAmountResult yearAmountResult_1 = yearAmountResultList.get(0);
    assertTrue(2015 == yearAmountResult_1.getYear());
    assertTrue(1002 == yearAmountResult_1.getAmount());

    YearAmountResult yearAmountResult_2 = yearAmountResultList.get(1);
    assertTrue(2015 == yearAmountResult_2.getYear());
    assertTrue(1002 == yearAmountResult_2.getAmount());
  }

  @Test
  public void getAmountListTest() {
    Institution institution = institutionRepository.findByName("국민은행");
    List<HouseFund> houseFundList = houseFundService.getAmountList(institution);
    assertNotNull(houseFundList);

    HouseFund houseFund_1 = houseFundList.get(0);
    assertEquals(Year.of(2015), houseFund_1.getYear());
    assertEquals(Month.of(1), houseFund_1.getMonth());
    assertEquals("국민은행", houseFund_1.getInstitution().getName());
    assertTrue(1001 == houseFund_1.getAmount());

    HouseFund houseFund_2 = houseFundList.get(1);
    assertEquals(Year.of(2015), houseFund_2.getYear());
    assertEquals(Month.of(2), houseFund_2.getMonth());
    assertEquals("국민은행", houseFund_2.getInstitution().getName());
    assertTrue(1002 == houseFund_2.getAmount());
  }
}
