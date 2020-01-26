package com.hyunzzang.financial.house.application.interceptor;

import com.hyunzzang.financial.house.common.constant.AuthConstant;
import com.hyunzzang.financial.house.common.exception.AccountErrorMessage;
import com.hyunzzang.financial.house.common.exception.AccountException;
import com.hyunzzang.financial.house.domain.auth.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AccountInterceptor extends HandlerInterceptorAdapter {

    private AccountService accountService;

    @Autowired
    private AccountInterceptor(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[postHandle][" + request + "]");

        return authenticate(request, response);
    }

    private boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        log.info(":: authenticate ::");

        String authHeader = request.getHeader(AuthConstant.HEADER_KEY_AUTHORIZATION);
        log.debug("Auth header : {}", authHeader);
        if (StringUtils.isEmpty(authHeader)) {
            throw new AccountException(AccountErrorMessage.TOKEN_NONE);
        }

        if (StringUtils.contains(authHeader, AuthConstant.HEADER_VAL_TOKEN_REFRESH)) {
            String oldToken = StringUtils.removeStart(authHeader, AuthConstant.HEADER_VAL_TOKEN_REFRESH);
            String refreshToken = accountService.refreshToken(oldToken);
            response.setHeader(AuthConstant.HEADER_KEY_AUTHORIZATION, refreshToken);
            return true;
        } else if (accountService.verifyToken(authHeader)) {
            return true;
        }

        throw new AccountException(AccountErrorMessage.TOKEN_INVALID);
    }
}
