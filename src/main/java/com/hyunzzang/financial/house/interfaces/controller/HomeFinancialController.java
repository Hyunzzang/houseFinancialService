package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.application.dto.HouseFinancialCsvResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/finance/home")
public class HomeFinancialController {

    private HouseFinancialCsvService houseFinancialCsvService;

    public HomeFinancialController(HouseFinancialCsvService houseFinancialCsvService) {
        this.houseFinancialCsvService = houseFinancialCsvService;
    }

    @PostMapping("/csv")
    public ResponseEntity<HouseFinancialCsvResult> saveHomeFinancialFromCsv(MultipartFile file) {
        log.info(":: saveHomeFinancialFromCsv ::");

        return new ResponseEntity(houseFinancialCsvService.addHouseFinancial(file), HttpStatus.OK);
    }
}
