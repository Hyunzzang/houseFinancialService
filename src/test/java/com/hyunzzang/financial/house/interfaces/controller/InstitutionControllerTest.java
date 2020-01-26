package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.common.constant.AuthConstant;
import com.hyunzzang.financial.house.common.dto.InstitutionResponse;
import com.hyunzzang.financial.house.domain.auth.Account;
import com.hyunzzang.financial.house.domain.auth.TokenService;
import com.hyunzzang.financial.house.domain.institution.InstitutionService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InstitutionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService JwtTokenService;

    @MockBean
    private InstitutionService institutionService;


    @Test
    @DisplayName("주택금융 공급 금융기관(은행) 목록을 출력하는 API")
    public void list_mock_테스트() throws Exception {
        List<InstitutionResponse> institutionResponseList = new ArrayList<>();
        institutionResponseList.add(new InstitutionResponse("국민은행"));
        institutionResponseList.add(new InstitutionResponse("하나은행"));

        given(institutionService.getAllInstitution()).willReturn(institutionResponseList);

        mvc.perform(get("/api/institution/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AuthConstant.HEADER_KEY_AUTHORIZATION, getToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("국민은행"))
                .andExpect(jsonPath("$[1].name").value("하나은행"));
    }

    private String getToken() throws NoSuchAlgorithmException {
        return JwtTokenService.generate(
                Account.builder()
                        .authId("testId")
                        .pw("123456")
                        .build());
    }
}
