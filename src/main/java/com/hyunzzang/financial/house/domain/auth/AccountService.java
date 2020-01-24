package com.hyunzzang.financial.house.domain.auth;

import com.hyunzzang.financial.house.common.dto.auth.AccountRequest;
import com.hyunzzang.financial.house.common.dto.auth.AccountResponse;
import com.hyunzzang.financial.house.common.exception.AccountErrorMessage;
import com.hyunzzang.financial.house.common.exception.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class AccountService {

    private AccountRepository accountRepository;

    private TokenService tokenService;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          @Qualifier("jwtTokenService") TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
    }

    public AccountResponse join(AccountRequest accountRequest) {
        Account account;
        try {
            account = Account.builder()
                    .authId(accountRequest.getAuthId())
                    .pw(accountRequest.getPw())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new AccountException(AccountErrorMessage.ACCOUNT_PW_ERROR);
        }

        String token = tokenService.generate(account);
        log.debug("token : {}", token);

        accountRepository.save(account);

        return new AccountResponse(account.getAuthId(), token);
    }

    public String login(AccountRequest accountRequest) {
        Account account = accountRepository.findByAuthId(accountRequest.getAuthId());
        if (account == null) {
            throw new AccountException(AccountErrorMessage.ACCOUNT_NONE);
        }
        try {
            if (!account.verifyPassword(accountRequest.getPw())) {
                throw new AccountException(AccountErrorMessage.ACCOUNT_PW_VALIDATION_FAILED);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AccountException(AccountErrorMessage.ACCOUNT_PW_ERROR);
        }

        return tokenService.generate(account);
    }


}
