package com.hyunzzang.financial.house.application.interceptor;

import com.hyunzzang.financial.house.common.constant.AuthConstant;
import com.hyunzzang.financial.house.common.exception.AccountErrorMessage;
import com.hyunzzang.financial.house.common.exception.AccountException;
import com.hyunzzang.financial.house.domain.auth.Account;
import com.hyunzzang.financial.house.domain.auth.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountInterceptorTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenService JwtTokenService;

    @Test
    public void 토큰검증_테스트_성() throws Exception {
        mvc.perform(get("/api/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AuthConstant.HEADER_KEY_AUTHORIZATION, getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void 토큰검증_테스트_실패() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AuthConstant.HEADER_KEY_AUTHORIZATION, getToken()+"a"))
                .andExpect(status().is(HttpStatus.EXPECTATION_FAILED.value()))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(),
                is(AccountErrorMessage.TOKEN_VERIFICATION_FAIL.getMessage()));
    }

    @Test
    public void 토큰재발급_테스트_성공() throws Exception {
        String token = getToken();
        String reToken = AuthConstant.HEADER_VAL_TOKEN_REFRESH + " " + token;
        MvcResult mvcResult = mvc.perform(get("/api/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AuthConstant.HEADER_KEY_AUTHORIZATION, reToken))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader(AuthConstant.HEADER_KEY_AUTHORIZATION), not(token));
    }

    @Test
    public void 토큰없을_경우() throws Exception {
        String reToken = getToken();
        MvcResult mvcResult = mvc.perform(get("/api/finance/house/totalYear")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.EXPECTATION_FAILED.value()))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(),
                is(AccountErrorMessage.TOKEN_NONE.getMessage()));
    }


    private String getToken() throws NoSuchAlgorithmException {
        return JwtTokenService.generate(
                Account.builder()
                        .authId("testId")
                        .pw("123456")
                        .build());
    }
}
