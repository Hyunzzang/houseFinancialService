package com.hyunzzang.financial.house.application;

import com.hyunzzang.financial.house.application.dto.HouseFinancialCsvResult;
import com.hyunzzang.financial.house.common.CSVReaderUtil;
import com.hyunzzang.financial.house.domain.fund.HouseFund;
import com.hyunzzang.financial.house.domain.institution.Institution;
import com.hyunzzang.financial.house.domain.institution.InstitutionService;
import com.hyunzzang.financial.house.domain.institution.InstitutionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class HouseFinancialCsvService {

    private InstitutionService institutionService;

    @Autowired
    public HouseFinancialCsvService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @Transactional
    public HouseFinancialCsvResult addHouseFinancial(MultipartFile file) {
        // todo 헤더와 본문을 분리 해서 가져와야 하지 않을까?
        List<String[]> rowList = CSVReaderUtil.readAll(file);
        // todo rowList 검증 해야 하지 않을까? 그리고 rowList가 많을 경우에는?
        Map<Integer, Institution> institutionMap = makeInstitutionMap(rowList.get(0));
        institutionService.addInstitutions(institutionMap.values());
        List<HouseFund> houseFundList = makeHouseFundToList(rowList, institutionMap);

        return new HouseFinancialCsvResult(institutionMap.size(), houseFundList.size());
    }

    private Map<Integer, Institution> makeInstitutionMap(String[] row) {
        Map<Integer, Institution> resMap = new HashMap<>();
        for (int i = 2; i < row.length; i++) {
            InstitutionType institutionType = InstitutionType.getInstitutionTypeByName(row[i]);
            // todo institutionType 타입이 NONE 이면 예외처리를 하자.

            resMap.put(i, Institution.ByInstitutionTypeBuilder().institutionType(institutionType).build());
        }

        return resMap;
    }

    private List<HouseFund> makeHouseFundToList(final List<String[]> rowList, final Map<Integer, Institution> resMap) {
        List<HouseFund> resList = new ArrayList<>();
        for (int i = 1; i < rowList.size(); i++) {
            resList.addAll(makeHouseFund(i, rowList.get(i), resMap));
        }

        return resList;
    }

    private List<HouseFund> makeHouseFund(final int rowNo, final String[] row, final Map<Integer, Institution> resMap) {
        log.debug("row - {} - data : {}", rowNo, StringUtils.join(row, ", "));

        List<HouseFund> houseFundList = new ArrayList<>();
        Assert.isTrue(NumberUtils.isCreatable(row[0]), "1번째 컬럼 숫자형이 아닙니다.");
        Assert.isTrue(NumberUtils.isCreatable(row[1]), "2번째 컬럼 숫자형이 아닙니다.");
        int year = NumberUtils.toInt(row[0]);
        int month = NumberUtils.toInt(row[1]);

        for (int i = 2; i < row.length; i++) {
            Assert.isTrue(NumberUtils.isCreatable(row[i]), i + "번째 컬럼 숫자형이 아닙니다.");
            houseFundList.add(HouseFund.builder()
                    .year(year)
                    .month(month)
                    .institution(resMap.get(i))
                    .amount(NumberUtils.toLong(row[i]))
                    .build());
        }

        return houseFundList;
    }
}
