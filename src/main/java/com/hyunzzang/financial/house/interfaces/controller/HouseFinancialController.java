package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.common.dto.HouseFinancialCsvResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/finance/hose")
public class HouseFinancialController {

    private HouseFinancialCsvService houseFinancialCsvService;

    public HouseFinancialController(HouseFinancialCsvService houseFinancialCsvService) {
        this.houseFinancialCsvService = houseFinancialCsvService;
    }

    @PostMapping("/csv")
    public ResponseEntity<HouseFinancialCsvResult> saveHomeFinancialFromCsv(MultipartFile file) {
        log.info(":: saveHomeFinancialFromCsv ::");

        return ResponseEntity.ok(houseFinancialCsvService.addHouseFinancial(file));
    }
}
