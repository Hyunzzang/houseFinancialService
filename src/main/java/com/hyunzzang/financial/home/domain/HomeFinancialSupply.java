package com.hyunzzang.financial.home.domain;

import javax.persistence.*;

@Entity
public class HomeFinancialSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long policyId;

    private Integer year;

    private Integer month;

    private Long housingCityFund;

    private Long kbBank;

    private Long wooriBank;

    private Long shinhanBank;

    private Long korCitiBank;

    private Long hanaBank;

    private Long nonghyupSuhyupBank;

    private Long kebBank;

    private Long otherBank;


}
