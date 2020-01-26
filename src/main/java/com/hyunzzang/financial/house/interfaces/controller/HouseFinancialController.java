package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.application.HouseFinancialCsvService;
import com.hyunzzang.financial.house.application.HouseFinancialSearchService;
import com.hyunzzang.financial.house.common.dto.*;
import com.hyunzzang.financial.house.domain.fund.HouseFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/fund")
public class HouseFinancialController {

    private HouseFinancialCsvService houseFinancialCsvService;
    private HouseFundService houseFundService;
    private HouseFinancialSearchService houseFinancialSearchService;

    @Autowired
    public HouseFinancialController(HouseFinancialCsvService houseFinancialCsvService,
                                    HouseFundService houseFundService,
                                    HouseFinancialSearchService houseFinancialSearchService) {
        this.houseFinancialCsvService = houseFinancialCsvService;
        this.houseFundService = houseFundService;
        this.houseFinancialSearchService = houseFinancialSearchService;
    }

    /**
     * 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API
     * @param file
     * @return
     */
    @PostMapping("/csv")
    public ResponseEntity<HouseFinancialCsvResult> saveHomeFinancialFromCsv(MultipartFile file) {
        log.info(":: saveHomeFinancialFromCsv ::");

        // todo 중복된 데이터 추가시 예외 처리를 하자.
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
    public ResponseEntity<YearMaxInstitutionResponse> getMaxBankYear() {
        log.info(":: getMaxAdmountYear ::");

        return ResponseEntity.ok(houseFundService.getYearMaxAmountInstitution());
    }

    /**
     * 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
     *
     * @param bankName
     * @return
     */
    @GetMapping("/average/{bankName}")
    public ResponseEntity<BankAverageResponse> getAverageForBank(@PathVariable("bankName") String bankName) {
        log.info(":: getAverageForBank ::");
        log.debug("bankName : {}", bankName);

        return ResponseEntity.ok(houseFinancialSearchService.getMaxMinAvgAmountForBank(bankName));
    }

    @GetMapping("/estimate/{bankName}/{month}")
    public ResponseEntity<EstimateAmountResponse> getEstimateForBank(
            @PathVariable("bankName") String bankName,
            @PathVariable("month") int month) {
        log.info(":: getEstimateForBank ::");
        log.debug("bankName : {}", bankName);
        log.debug("month : {}", month);

        return ResponseEntity.ok(houseFinancialSearchService.getEstimateAmount(bankName, month));
    }
}
