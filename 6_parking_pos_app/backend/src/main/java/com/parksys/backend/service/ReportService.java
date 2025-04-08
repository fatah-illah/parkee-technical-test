package com.parksys.backend.service;

import com.parksys.backend.dto.DailyRevenueDTO;
import com.parksys.backend.dto.OccupancyReportDTO;
import com.parksys.backend.dto.VehicleTypeDistributionDTO;
import com.parksys.backend.repository.ParkingTicketRepository;
import com.parksys.backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ParkingTicketRepository parkingTicketRepository;

    private final PaymentRepository paymentRepository;

    public ReportService(ParkingTicketRepository parkingTicketRepository, PaymentRepository paymentRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<DailyRevenueDTO> getDailyRevenue(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findDailyRevenueBetweenDates(startDate, endDate);
    }

    public BigDecimal getTotalRevenue(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return paymentRepository.calculateTotalRevenueBetween(startOfDay, endOfDay);
    }

    public OccupancyReportDTO getCurrentOccupancy() {
        long totalSpaces = 500; // Configure based on your parking lot size
        long occupiedSpaces = parkingTicketRepository.countByStatus("active");

        return new OccupancyReportDTO(
                totalSpaces,
                occupiedSpaces,
                totalSpaces - occupiedSpaces,
                (double) occupiedSpaces / totalSpaces * 100
        );
    }

    public List<VehicleTypeDistributionDTO> getVehicleTypeDistribution(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return parkingTicketRepository.countVehicleTypesBetween(startOfDay, endOfDay);
    }

    public Map<String, Long> getHourlyOccupancy(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Object[]> results = parkingTicketRepository.countActiveTicketsByHour(startOfDay, endOfDay);
        return results.stream().collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Number) row[1]).longValue()
        ));
    }

    public BigDecimal getAverageStayDuration(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return parkingTicketRepository.calculateAverageStayDuration(startOfDay, endOfDay);
    }
}
