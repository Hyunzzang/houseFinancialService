package com.hyunzzang.financial.house.interfaces.handlig;

import com.hyunzzang.financial.house.common.exception.HomeFinancialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = HomeFinancialException.class)
    public ResponseEntity handelException(HomeFinancialException e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(e.getHomeFinancialErrorMessage());
    }
}
