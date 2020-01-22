package com.hyunzzang.financial.house.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class YearTotalAmountResponse {
    private int year;

    @JsonProperty("label")
    private long totalAmount;

    @JsonProperty("label")
    private Map<String, Long> detaailAmount;
}
