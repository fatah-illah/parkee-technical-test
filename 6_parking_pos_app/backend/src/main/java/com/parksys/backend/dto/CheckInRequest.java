package com.parksys.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CheckInRequest {
    @NotBlank(message = "Plate number is required")
    private String plateNumber;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    private String slipNumber;
    private String gateSystem;
}
