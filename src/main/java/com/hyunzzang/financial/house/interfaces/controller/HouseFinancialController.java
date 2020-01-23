package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.common.dto.HouseFinancialCsvResult;
import com.hyunzzang.financial.house.common.dto.YearMaxInstitutionResponse;
import com.hyunzzang.financial.house.common.dto.YearTotalAmountResponse;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/finance/house")
public class HouseFinancialController {

    private HouseFinancialCsvService houseFinancialCsvService;
    private HouseFundService houseFundService;

    @Autowired
    public HouseFinancialController(HouseFinancialCsvService houseFinancialCsvService, HouseFundService houseFundService) {
        this.houseFinancialCsvService = houseFinancialCsvService;
        this.houseFundService = houseFundService;
    }

    /**
     * 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
     * @param file
     * @return
     */
    @PostMapping("/csv")
    public ResponseEntity<HouseFinancialCsvResult> saveHomeFinancialFromCsv(MultipartFile file) {
        log.info(":: saveHomeFinancialFromCsv ::");

        return ResponseEntity.ok(houseFinancialCsvService.addHouseFinancial(file));
    }

    /**
     * 년도별 각 금융기관의 지원금액 합계를 출력하는 API
     * @return
     */
    @GetMapping("/totalYear")
    public ResponseEntity<List<YearTotalAmountResponse>> getTotalYear() {
        log.info(":: getTotalYear ::");

        return ResponseEntity.ok(houseFundService.getAllYearSumAmount());
    }

    /**
     * 각년도별 각기관의 전체지원금액중에서 가장 큰금액의 기관명을 출력하는 API
     * @return
     */
    @GetMapping("/maxBank")
    public ResponseEntity<List<YearMaxInstitutionResponse>> getMaxBankYear() {
        log.info(":: getMaxAdmountYear ::");

        return ResponseEntity.ok(houseFundService.getYearMaxAmountInstitution());
    }
}
