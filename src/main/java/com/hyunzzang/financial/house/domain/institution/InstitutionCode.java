package com.hyunzzang.financial.house.domain.institution;

import lombok.Getter;
import lombok.ToString;

import java.util.EnumSet;

@Getter
@ToString
public enum InstitutionCode {
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

  private String code;
  private String name;

  InstitutionCode(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static InstitutionCode getInstitutionTypeByName(String inName) {
    EnumSet<InstitutionCode> enumSet = EnumSet.allOf(InstitutionCode.class);
    for (InstitutionCode code : enumSet) {
      if (code.getName().equals(inName)) {
        return code;
      }
    }

    // todo 예외처리를 하자.
    return NONE;
  }
}

