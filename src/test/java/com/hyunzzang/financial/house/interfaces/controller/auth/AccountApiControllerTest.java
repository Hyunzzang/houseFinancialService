package com.hyunzzang.financial.house.interfaces.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunzzang.financial.house.common.constant.AuthConstant;
import com.hyunzzang.financial.house.common.dto.auth.AccountRequest;
import com.hyunzzang.financial.house.common.dto.auth.AccountResponse;
import com.hyunzzang.financial.house.domain.auth.AccountService;
import com.hyunzzang.financial.house.domain.auth.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountApiController.class)
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    @Qualifier("jwtTokenService")
    private TokenService tokenService;



    @Test
    public void signup_mock_테스트() throws Exception {
        AccountRequest accountRequest = new AccountRequest("testId", "testPw");
        AccountResponse accountResponse = new AccountResponse("testId", "testToken");

        given(accountService.join(any(AccountRequest.class))).willReturn(accountResponse);

        MvcResult mvcResult = mvc.perform(post("/api/account/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("authId").value("testId"))
                .andExpect(jsonPath("token").value("testToken"))
                .andReturn();

        String token = mvcResult.getResponse().getHeader(AuthConstant.HEADER_KEY_AUTHORIZATION);
        assertEquals("testToken", token);
    }


    @Test
    public void signin_mock_테스트() throws Exception {
        AccountRequest accountRequest = new AccountRequest("testId", "testPw");
        AccountResponse accountResponse = new AccountResponse("testId", "testToken");

        given(accountService.login(any(AccountRequest.class))).willReturn(accountResponse);

        MvcResult mvcResult = mvc.perform(post("/api/account/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("authId").value("testId"))
                .andExpect(jsonPath("token").value("testToken"))
                .andReturn();
        String token = mvcResult.getResponse().getHeader(AuthConstant.HEADER_KEY_AUTHORIZATION);
        assertEquals("testToken", token);
    }
}
