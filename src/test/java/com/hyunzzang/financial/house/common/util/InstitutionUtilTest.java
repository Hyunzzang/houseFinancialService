package com.hyunzzang.financial.house.common.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstitutionUtilTest {

  @Test
  public void exclusionNameTest() {
    String bankName = "신한은행(억원)";

    String res = InstitutionUtil.exclusionName(bankName);
    assertEquals("신한은행", res);
  }
}
