package com.hyunzzang.financial.house.domain.institution;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="institution")
public class Institution {
    // todo type 소스상에 정의 해야 할까?
    @Id
    @Column(name = "institution_id", nullable = false)
    private String type;

    @Column(name = "name", nullable = false)
    private String name;


    @Builder(builderMethodName = "ByInstitutionTypeBuilder")
    public Institution(InstitutionType institutionType) {
        this.type = institutionType.getType();
        this.name = institutionType.getName();
    }
}
