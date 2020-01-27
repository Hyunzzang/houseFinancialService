package com.hyunzzang.financial.house.application;


import com.hyunzzang.financial.house.common.dto.HouseFinancialCsvResult;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import com.hyunzzang.financial.house.domain.institution.InstitutionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseFinancialCsvServiceTest {

  @Autowired
  private HouseFinancialCsvService houseFinancialCsvService;

  @MockBean
  private InstitutionService institutionService;

  @MockBean
  private HouseFundService houseFundService;

  @Test
  public void addHouseFinancialTest() {
    String content = "연도,월,주택도시기금1)(억원),국민은행(억원),우리은행(억원),신한은행(억원),한국시티은행(억원),하나은행(억원)," +
        "농협은행/수협은행(억원),외환은행(억원),기타은행(억원),,,,,,,\n" +
        "2005,1,1019,846,82,95,30,157,57,80,99,,,,,,,\n" +
        "2005,2,1144,864,91,97,35,168,36,111,114,,,,,,,\n" +
        "2006,1,1214,534,127,79,21,210,68,245,63,,,,,,,\n" +
        "2006,2,1618,416,145,101,24,238,59,124,80,,,,,,,\n" +
        "2007,1,1187,282,134,63,9,196,36,259,72,,,,,,,\n" +
        "2007,2,1610,263,135,102,9,189,172,67,61,,,,,,,\n";

    MockMultipartFile file = new MockMultipartFile("data", "data.csv", "text/plain",
        content.getBytes());
    HouseFinancialCsvResult csvResult = houseFinancialCsvService.addHouseFinancial(file);
    assertNotNull(csvResult);
    assertTrue(9 == csvResult.getAddedInstitutionCount());
    assertTrue((9 * 6) == csvResult.getAddedHouseFundCount());
  }
}
