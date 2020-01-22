package com.hyunzzang.financial.house.domain.institution;

import lombok.Getter;
import lombok.ToString;

import java.util.EnumSet;

@Getter
@ToString
public enum InstitutionType {
    NHUF("NHUF", "주택도시기금"),
    KB_BANK("KB_BANK", "국민은행"),
    WOORI_BANK("WOORI_BANK", "우리은행"),
    SHINHAN_BANK("SHINHAN_BANK", "신한은행"),
    KOR_CITY_BANK("KOR_CITY_BANK", "한국시티은행"),
    HANA_BANK("HANA_BANK", "하나은행"),
    NH_SH_BANK("NH_SH_BANK", "농협은행/수협은행"),
    KEB_BANK("KEB_BANK", "외환은행"),
    ETC_BANK("ETC_BANK", "기타은행"),
    NONE("NONE", "알수없음");

    private String type;
    private String name;

    InstitutionType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static InstitutionType getInstitutionTypeByName(String inName) {
        EnumSet<InstitutionType> enumSet = EnumSet.allOf(InstitutionType.class);
        for (InstitutionType type: enumSet) {
            if (type.getName().equals(inName)) {
                return type;
            }
        }

        return NONE;
    }
}

