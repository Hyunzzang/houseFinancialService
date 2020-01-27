package com.hyunzzang.financial.house.common.util;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CSVReaderUtilTest {

  @Test
  public void readAllTest() {
    String content = "2007,1,1187,282,134,63,9,196,36,259,72\n" +
        "2007,2,1610,263,135,102,9,189,172,67,61\n";

    MockMultipartFile file = new MockMultipartFile("data", "data.csv", "text/plain",
        content.getBytes());
    List<String[]> csvRes = CSVReaderUtil.readAll(file);
    assertTrue(csvRes.size() == 2);

    String[] contest_1 = {"2007", "1", "1187", "282", "134", "63", "9", "196", "36", "259", "72"};
    assertArrayEquals(contest_1, csvRes.get(0));

    String[] contest_2 = {"2007", "2", "1610", "263", "135", "102", "9", "189", "172", "67", "61"};
    assertArrayEquals(contest_2, csvRes.get(1));
  }
}
