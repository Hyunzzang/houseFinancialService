package com.hyunzzang.financial.house.domain.institution;

import com.hyunzzang.financial.house.common.dto.InstitutionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Institution> addInstitutions(Collection<Institution> institutionSet) {
        return institutionRepository.saveAll(institutionSet);
    }

    public List<InstitutionResponse> getAllInstitution() {
        return institutionRepository.findAll().stream()
                .map(i -> new InstitutionResponse(i.getName()))
                .collect(Collectors.toList());
    }
}
