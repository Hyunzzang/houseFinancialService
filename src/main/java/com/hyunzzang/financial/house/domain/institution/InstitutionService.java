package com.hyunzzang.financial.house.domain.institution;

import com.hyunzzang.financial.house.common.dto.InstitutionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstitutionService {

    private InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Transactional(readOnly = true)
    public List<Institution> addInstitutions(Collection<Institution> institutionSet) {
        return institutionRepository.saveAll(institutionSet);
    }

    @Transactional(readOnly = true)
    public List<InstitutionResponse> getAllInstitution() {
        return institutionRepository.findAll().stream()
                .map(i -> new InstitutionResponse(i.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Institution getInstitutionByName(String name) {
        return institutionRepository.findByName(name);
    }
}
