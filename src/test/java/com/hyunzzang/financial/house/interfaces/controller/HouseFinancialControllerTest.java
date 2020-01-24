package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.application.HouseFinancialSearchService;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import com.hyunzzang.financial.house.domain.auth.Account;
import com.hyunzzang.financial.house.domain.auth.TokenService;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import com.hyunzzang.financial.house.domain.fund.YearSumAmountResult;
import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionCode;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.NoSuchAlgorithmException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HouseFinancialControllerTest {
    private static final String HEADER_KEY_AUTHORIZATION = "Authorization";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService JwtTokenService;

    @MockBean
    private HouseFinancialCsvService houseFinancialCsvService;

    @MockBean
    private HouseFundService houseFundService;

    @MockBean
    private HouseFinancialSearchService houseFinancialSearchService;


//    @Test
//    @DisplayName("데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API")
//    public void saveHomeFinancialFromCsv_mock_테스트() throws Exception {
//        HouseFinancialCsvResult houseFinancialCsvResult = new HouseFinancialCsvResult(5, 100);
//        MockMultipartFile file = new MockMultipartFile("data", "data.csv", "text/plain", "2005,1,1019,846,82,95,30,157,57,80,99,,,,,,,".getBytes());
//
//        given(houseFinancialCsvService.addHouseFinancial(file)).willReturn(houseFinancialCsvResult);
//
//        mvc.perform(MockMvcRequestBuilders.multipart("/api/finance/house/csv")
//                .file(file)
//                .header(HEADER_KEY_AUTHORIZATION, getToken()))
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.addedInstitutionCount").value(5))
//                .andExpect(jsonPath("$.addedHouseFundCount").value(100));
//
//    }


    @Test
    @DisplayName("년도별 각 금융기관의 지원금액 합계를 출력하는 API")
    public void getTotalYear_mock_테스트() throws Exception {
        List<YearSumAmountResult> yearSumAmountResultList = new ArrayList<>();
        yearSumAmountResultList.add(new YearSumAmountResult(Year.of(2017), new Institution(InstitutionCode.KB_BANK), 1200L));
        List<YearTotalAmountResponse> yearTotalAmountResponseList = new ArrayList<>();
        yearTotalAmountResponseList.add(new YearTotalAmountResponse(Year.of(2017), yearSumAmountResultList));

        given(houseFundService.getAllYearSumAmount()).willReturn(yearTotalAmountResponseList);

        mvc.perform(get("/api/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_KEY_AUTHORIZATION, getToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year").value(2017))
                .andExpect(jsonPath("$[0].total_amount").value(1200L))
                .andExpect(jsonPath("$[0].detail_amount.국민은행").value(1200L));
    }

    @Test
    @DisplayName("각년도별 각기관의 전체지원금액중에서 가장 큰금액의 기관명을 출력하는 API")
    public void getMaxBankYear_mock_테스트() {

    }


    private String getToken() throws NoSuchAlgorithmException {
        return JwtTokenService.generate(
                Account.builder()
                        .authId("testId")
                        .pw("123456")
                        .build());
    }

}
