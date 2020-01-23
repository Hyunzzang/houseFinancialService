package com.hyunzzang.financial.house.interfaces;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.application.HouseFinancialSearchService;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import com.hyunzzang.financial.house.domain.fund.YearSumAmountResult;
import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionCode;
import com.hyunzzang.financial.house.interfaces.controller.HouseFinancialController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HouseFinancialController.class)
public class HouseFinancialControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HouseFinancialCsvService houseFinancialCsvService;

    @MockBean
    private HouseFundService houseFundService;

    @MockBean
    private HouseFinancialSearchService houseFinancialSearchService;


    @Test
    @DisplayName("년도별 각 금융기관의 지원금액 합계를 출력하는 API")
    public void getTotalYear_mock_테스트() throws Exception {
        List<YearSumAmountResult> yearSumAmountResultList = new ArrayList<>();
        yearSumAmountResultList.add(new YearSumAmountResult(Year.of(2017), new Institution(InstitutionCode.KB_BANK), 1200L));
        List<YearTotalAmountResponse> yearTotalAmountResponseList = new ArrayList<>();
        yearTotalAmountResponseList.add(new YearTotalAmountResponse(Year.of(2017), yearSumAmountResultList));

        given(houseFundService.getAllYearSumAmount()).willReturn(yearTotalAmountResponseList);

        mvc.perform(get("/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year").value(2017))
                .andExpect(jsonPath("$[0].total_amount").value(1200L))
                .andExpect(jsonPath("$[0].detail_amount.국민은").value(1200L));
    }

    @Test
    public void getMaxBankYear_mock_테스트() {

    }

}
