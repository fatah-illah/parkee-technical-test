package com.parksys.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CheckOutRequest {
    @NotBlank(message = "Plate number is required")
    private String plateNumber;

    private String paymentMethod;
    private String memberType;
    private String voucherCode;
}
