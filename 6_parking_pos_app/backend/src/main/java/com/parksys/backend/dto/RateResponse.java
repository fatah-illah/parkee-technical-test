package com.parksys.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateResponse {
    private Long id;
    private String vehicleType;
    private BigDecimal hourlyRate;
    private BigDecimal maxDailyRate;
    private BigDecimal overnightSurcharge;
}
