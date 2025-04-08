package com.parksys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OccupancyReportDTO {
    private long totalSpaces;
    private long occupiedSpaces;
    private long availableSpaces;
    private double occupancyPercentage;
}
