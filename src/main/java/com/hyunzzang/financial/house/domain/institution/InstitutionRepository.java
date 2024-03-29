package com.hyunzzang.financial.house.domain.institution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

  Institution findByName(String name);
}
