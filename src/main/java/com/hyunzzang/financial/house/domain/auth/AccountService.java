package com.hyunzzang.financial.house.domain.auth;

import com.hyunzzang.financial.house.common.dto.auth.AccountSignupRequest;
import com.hyunzzang.financial.house.common.dto.auth.AccountSignupResponse;
import com.hyunzzang.financial.house.common.exception.HouseFinancialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class AccountService {

    private AccountRepository accountRepository;

    private TokenGenerator tokenGenerator;

    @Autowired
    public AccountService(AccountRepository accountRepository, TokenGenerator tokenGenerator) {
        this.accountRepository = accountRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public AccountSignupResponse join(AccountSignupRequest accountSignupRequest) {
        Account account;
        try {
            account = Account.builder()
                    .authId(accountSignupRequest.getAuthId())
                    .pw(accountSignupRequest.getPw())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            // todo 예외 처리를 하자.
            throw new HouseFinancialException(null);
        }

        String token = tokenGenerator.generate(account);

        accountRepository.save(account);

        return new AccountSignupResponse(account.getAuthId(), token);
    }
}
