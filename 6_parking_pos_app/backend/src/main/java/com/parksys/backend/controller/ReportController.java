package com.parksys.backend.controller;

import com.parksys.backend.dto.DailyRevenueDTO;
import com.parksys.backend.dto.OccupancyReportDTO;
import com.parksys.backend.dto.VehicleTypeDistributionDTO;
import com.parksys.backend.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/revenue/daily")
    public ResponseEntity<List<DailyRevenueDTO>> getDailyRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getDailyRevenue(startDate, endDate));
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<BigDecimal> getTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getTotalRevenue(date));
    }

    @GetMapping("/occupancy/current")
    public ResponseEntity<OccupancyReportDTO> getCurrentOccupancy() {
        return ResponseEntity.ok(reportService.getCurrentOccupancy());
    }

    @GetMapping("/vehicle-types")
    public ResponseEntity<List<VehicleTypeDistributionDTO>> getVehicleTypeDistribution(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getVehicleTypeDistribution(date));
    }

    @GetMapping("/occupancy/hourly")
    public ResponseEntity<Map<String, Long>> getHourlyOccupancy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getHourlyOccupancy(date));
    }

    @GetMapping("/average-stay")
    public ResponseEntity<BigDecimal> getAverageStayDuration(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getAverageStayDuration(date));
    }
}
