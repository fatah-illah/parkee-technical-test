package com.parksys.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String vehicleType; // e.g. MOBIL, MOTOR

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
