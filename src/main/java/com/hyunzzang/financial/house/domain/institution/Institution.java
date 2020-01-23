package com.hyunzzang.financial.house.domain.institution;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="institution")
public class Institution {
    // todo type 소스상에 정의 해야 할까?
    @Id
    @Column(name = "id", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;


    @Builder(builderMethodName = "ByInstitutionTypeBuilder")
    public Institution(InstitutionCode institutionCode) {
        this.code = institutionCode.getCode();
        this.name = institutionCode.getName();
    }
}
