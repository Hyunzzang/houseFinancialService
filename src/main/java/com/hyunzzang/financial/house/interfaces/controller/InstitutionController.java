package com.hyunzzang.financial.house.interfaces.controller;

import com.hyunzzang.financial.house.common.dto.InstitutionResponse;
import com.hyunzzang.financial.house.domain.institution.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/institution")
public class InstitutionController {
    private InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    /**
     * 주택금융 공급 금융기관(은행) 목록을 출력하는 API
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<InstitutionResponse>> list() {
        return ResponseEntity.ok(institutionService.getAllInstitution());
    }
}
