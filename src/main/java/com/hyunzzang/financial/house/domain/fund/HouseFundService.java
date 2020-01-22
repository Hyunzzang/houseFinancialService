package com.hyunzzang.financial.house.domain.fund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class HouseFundService {
    private HouseFundRepository houseFundRepository;

    @Autowired
    public HouseFundService(HouseFundRepository houseFundRepository) {
        this.houseFundRepository = houseFundRepository;
    }

    public List<HouseFund> addAllHouseFund(Collection<HouseFund> houseFunds) {
        return houseFundRepository.saveAll(houseFunds);
    }
}
