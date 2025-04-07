package com.parksys.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateRequest {
    @NotBlank
    private String vehicleType;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal hourlyRate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal maxDailyRate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal overnightSurcharge;
}
