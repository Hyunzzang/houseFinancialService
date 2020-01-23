package com.hyunzzang.financial.house.domain.fund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseFundConditionSearchService {
    private HouseFundRepository houseFundRepository;

    @Autowired
    public HouseFundConditionSearchService(HouseFundRepository houseFundRepository) {
        this.houseFundRepository = houseFundRepository;
    }


}
