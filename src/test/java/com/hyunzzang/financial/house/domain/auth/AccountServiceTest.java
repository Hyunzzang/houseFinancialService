package com.hyunzzang.financial.house.domain.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AccountServiceTest {

  @Autowired
  private AccountService accountService;

  @Autowired
  @Qualifier("jwtTokenService")
  private TokenService tokenService;


  @Test
  public void refreshTokenTest() throws NoSuchAlgorithmException {
    Account account = Account.builder()
        .authId("testId")
        .pw("123456")
        .build();

    String testToken = tokenService.generate(account);
    assertNotNull(testToken);

    String refreshToken = accountService.refreshToken(testToken);
    assertNotNull(refreshToken);
    assertTrue(accountService.verifyToken(testToken));
  }


  @Test
  public void verifyTokenTest() throws NoSuchAlgorithmException {
    Account account = Account.builder()
        .authId("testId")
        .pw("123456")
        .build();

    String testToken = tokenService.generate(account);
    assertNotNull(testToken);
    assertTrue(accountService.verifyToken(testToken));
  }
}
