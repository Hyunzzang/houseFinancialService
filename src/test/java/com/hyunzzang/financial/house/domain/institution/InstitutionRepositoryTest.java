package com.hyunzzang.financial.house.domain.institution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstitutionRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepository;


    @Test
    public void whenFindByName_thenReturnInstitution() {
        Institution institution = Institution.ByInstitutionTypeBuilder()
                .institutionCode(InstitutionCode.HANA_BANK)
                .build();

        institutionRepository.save(institution);

        Institution found = institutionRepository.findByName(institution.getName());
        assertNotNull(found);
        assertEquals(found.getName(), institution.getName());
    }
}
