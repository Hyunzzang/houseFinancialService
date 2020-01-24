package com.hyunzzang.financial.house.application.interceptor;

import com.hyunzzang.financial.house.common.exception.AccountErrorMessage;
import com.hyunzzang.financial.house.common.exception.AccountException;
import com.hyunzzang.financial.house.domain.auth.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AccountInterceptor extends HandlerInterceptorAdapter {
    private static final String HEADER_KEY_AUTHORIZATION = "Authorization";
    private static final String HEADER_VAL_TOKEN_REFRESH = "Bearer_Token";

    private TokenService tokenService;

    @Autowired
    private AccountInterceptor(@Qualifier("jwtTokenService") TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[postHandle][" + request + "]");

        return authenticate(request, response);
    }

    private boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        log.info(":: authenticate ::");

        String authHeader = request.getHeader(HEADER_KEY_AUTHORIZATION);
        log.debug("Auth header : {}", authHeader);
        if (StringUtils.isEmpty(authHeader)) {
            throw new AccountException(AccountErrorMessage.TOKEN_NONE);
        }

        if (StringUtils.equals(HEADER_VAL_TOKEN_REFRESH, authHeader)) {
            String refreshToken = tokenService.refresh(authHeader);
            response.setHeader(HEADER_KEY_AUTHORIZATION, refreshToken);
            return true;
        } else if (tokenService.checkToken(authHeader)) {
            return true;
        }

        throw new AccountException(AccountErrorMessage.TOKEN_INVALID);
    }
}
