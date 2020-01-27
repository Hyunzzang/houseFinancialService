package com.hyunzzang.financial.house.domain.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;


  @Test
  public void findByAuthId_thenReturnAccount() throws NoSuchAlgorithmException {
    String authId = "testId";
    Account account = Account.builder()
        .authId(authId)
        .pw("123456")
        .build();
    accountRepository.save(account);

    Account resAccount = accountRepository.findByAuthId(authId);
    assertNotNull(resAccount);
    assertEquals(account.getAuthId(), resAccount.getAuthId());
    assertEquals(account.getPw(), resAccount.getPw());
  }
}
