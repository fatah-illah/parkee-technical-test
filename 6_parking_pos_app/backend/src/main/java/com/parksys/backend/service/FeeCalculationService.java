package com.parksys.backend.service;

import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.model.Rate;
import com.parksys.backend.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FeeCalculationService {

    private final RateRepository rateRepository;

    public FeeCalculationService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    private static final int GRACE_PERIOD_MINUTES = 15;

    public BigDecimal calculateFee(ParkingTicket ticket) {
        String vehicleType = ticket.getVehicle().getVehicleType();
        LocalDateTime checkInTime = ticket.getCheckInTime();
        LocalDateTime checkOutTime = ticket.getCheckOutTime();
        Duration parkingDuration = Duration.between(checkInTime, checkOutTime);

        if (parkingDuration.toMinutes() <= GRACE_PERIOD_MINUTES) {
            return BigDecimal.ZERO;
        }

        // Fetch rate from DB
        Rate rate = rateRepository.findByVehicleTypeIgnoreCase(vehicleType)
                .orElseThrow(() -> new RuntimeException("Rate not found for vehicle type: " + vehicleType));

        long hours = parkingDuration.toMinutes() / 60;
        if (parkingDuration.toMinutes() % 60 > 0) {
            hours++;
        }

        BigDecimal hourlyRate = rate.getHourlyRate();
        BigDecimal maxDailyRate = rate.getMaxDailyRate();
        BigDecimal overnightSurcharge = rate.getOvernightSurcharge();

        BigDecimal fee;
        long days = hours / 24;
        long remainingHours = hours % 24;

        BigDecimal remainingFee = hourlyRate.multiply(BigDecimal.valueOf(remainingHours));
        if (remainingFee.compareTo(maxDailyRate) > 0) {
            remainingFee = maxDailyRate;
        }

        fee = maxDailyRate.multiply(BigDecimal.valueOf(days)).add(remainingFee);

        // Overnight check
        LocalDateTime midnight = checkInTime.toLocalDate().plusDays(1).atStartOfDay();
        if (checkInTime.isBefore(midnight) && checkOutTime.isAfter(midnight)) {
            fee = fee.add(overnightSurcharge);
        }

        return fee;
    }

    public BigDecimal applyDiscounts(BigDecimal originalFee, String memberType, String voucherCode) {
        BigDecimal discount = BigDecimal.ZERO;

        if (memberType != null) {
            discount = switch (memberType.toUpperCase()) {
                case "PREMIUM" -> originalFee.multiply(new BigDecimal("0.20"));
                case "REGULAR" -> originalFee.multiply(new BigDecimal("0.10"));
                default -> discount;
            };
        }

        if (voucherCode != null && !voucherCode.isEmpty()) {
            if (voucherCode.equals("PARKFREE")) {
                discount = discount.add(new BigDecimal("10000"));
            }
        }

        return originalFee.subtract(discount).max(BigDecimal.ZERO);
    }
}
