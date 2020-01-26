package com.hyunzzang.financial.house.interfaces.controller.auth;

import com.hyunzzang.financial.house.common.constant.AuthConstant;
import com.hyunzzang.financial.house.common.dto.auth.AccountRequest;
import com.hyunzzang.financial.house.common.dto.auth.AccountResponse;
import com.hyunzzang.financial.house.domain.auth.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private AccountService accountService;

    @Autowired
    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * signup 계정생성 API
     *
     * @param accountRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> signup(@RequestBody AccountRequest accountRequest
            , HttpServletResponse response) {
        log.info(":: signup ::");
        log.debug("AuthId : {}", accountRequest.getAuthId());
        // todo request validator 해야 하지 않을까?

        AccountResponse accountResponse = accountService.join(accountRequest);
        response.setHeader(AuthConstant.HEADER_KEY_AUTHORIZATION, accountResponse.getToken());

        return ResponseEntity.ok(accountResponse);
    }

    /**
     * signin 로그인 API
     *
     * @param accountRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<AccountResponse> signin(@RequestBody AccountRequest accountRequest
            , HttpServletResponse response) {
        log.info(":: signin ::");
        log.debug("AuthId : {}", accountRequest.getAuthId());
        // todo request validator 해야 하지 않을까?

        AccountResponse accountResponse = accountService.login(accountRequest);
        response.setHeader(AuthConstant.HEADER_KEY_AUTHORIZATION, accountResponse.getToken());

        return ResponseEntity.ok(accountResponse);
    }
}
