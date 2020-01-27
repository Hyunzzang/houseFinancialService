package com.hyunzzang.financial.house.common.util;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class CipherUtilTest {

  @Test
  public void sha256Test() throws NoSuchAlgorithmException {
    String plaintext = "abcd12345!@#$";

    String reshash_1 = CipherUtil.sha256(plaintext);
    String reshash_2 = CipherUtil.sha256(plaintext);
    assertEquals(reshash_1, reshash_2);
  }

}
