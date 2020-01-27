package com.hyunzzang.financial.house.interfaces.handlig;

import com.hyunzzang.financial.house.common.exception.AccountException;
import com.hyunzzang.financial.house.common.exception.HouseFinancialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

  // todo response 에러 포맷을 정의 하자.
  @ResponseBody
  @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
  @org.springframework.web.bind.annotation.ExceptionHandler(value = HouseFinancialException.class)
  public ResponseEntity fundHandelException(HouseFinancialException e) {
    return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(e.getHouseFinancialErrorMessage().getMessage());
  }

  @ResponseBody
  @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
  @org.springframework.web.bind.annotation.ExceptionHandler(value = AccountException.class)
  public ResponseEntity authHandelException(AccountException e) {
    return ResponseEntity
        .status(HttpStatus.EXPECTATION_FAILED)
        .body(e.getAccountErrorMessage().getMessage());
  }
}
