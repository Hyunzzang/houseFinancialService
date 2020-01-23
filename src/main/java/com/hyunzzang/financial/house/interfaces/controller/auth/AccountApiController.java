package com.hyunzzang.financial.house.interfaces.controller.auth;

import com.hyunzzang.financial.house.common.dto.auth.AccountSignupRequest;
import com.hyunzzang.financial.house.common.dto.auth.AccountSignupResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    @PostMapping("/signup")
    public ResponseEntity<AccountSignupResponse> signup(AccountSignupRequest accountSignupRequest) {
        log.info(":: signup ::");
        log.debug("AuthId : {}", accountSignupRequest.getAuthId());
        // todo request validator 해야 하지 않을까?

        return ResponseEntity.ok(null);
    }
}
