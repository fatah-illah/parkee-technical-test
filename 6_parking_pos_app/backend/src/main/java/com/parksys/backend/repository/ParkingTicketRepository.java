package com.parksys.backend.repository;

import com.parksys.backend.dto.VehicleTypeDistributionDTO;
import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    List<ParkingTicket> findByVehicleAndStatus(Vehicle vehicle, String status);
    Optional<ParkingTicket> findBySlipNumber(String slipNumber);

    long countByStatus(String status);

    @Query("SELECT new com.parksys.backend.dto.VehicleTypeDistributionDTO(" +
            "v.vehicleType, COUNT(t)) " +
            "FROM ParkingTicket t JOIN t.vehicle v " +
            "WHERE t.checkInTime BETWEEN :startDateTime AND :endDateTime " +
            "GROUP BY v.vehicleType")
    List<VehicleTypeDistributionDTO> countVehicleTypesBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query(value = "SELECT TO_CHAR(t.check_in_time, 'HH24') AS hour, COUNT(*) AS count " +
            "FROM parking_tickets t " +
            "WHERE t.check_in_time BETWEEN :startDateTime AND :endDateTime " +
            "GROUP BY hour " +
            "ORDER BY hour", nativeQuery = true)
    List<Object[]> countActiveTicketsByHour(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (t.check_out_time - t.check_in_time)) / 3600.0) " +
            "FROM parking_tickets t " +
            "WHERE t.check_in_time BETWEEN :startDateTime AND :endDateTime " +
            "AND t.check_out_time IS NOT NULL", nativeQuery = true)
    BigDecimal calculateAverageStayDuration(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
