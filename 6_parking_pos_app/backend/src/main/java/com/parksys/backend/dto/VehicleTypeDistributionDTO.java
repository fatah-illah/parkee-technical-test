package com.parksys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleTypeDistributionDTO {
    private String vehicleType;
    private Long count;
}
