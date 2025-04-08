package com.parksys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyRevenueDTO {
    private LocalDate date;
    private BigDecimal totalAmount;
    private Long ticketCount;
}
