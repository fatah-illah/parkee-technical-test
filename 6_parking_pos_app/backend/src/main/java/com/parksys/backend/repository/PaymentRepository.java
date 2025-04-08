package com.parksys.backend.repository;

import com.parksys.backend.dto.DailyRevenueDTO;
import com.parksys.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT new com.parksys.backend.dto.DailyRevenueDTO(" +
            "CAST(p.paymentTime AS LocalDate), " +
            "SUM(p.totalPrice), " +
            "COUNT(p)) " +
            "FROM Payment p " +
            "WHERE CAST(p.paymentTime AS LocalDate) BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(p.paymentTime AS LocalDate) " +
            "ORDER BY CAST(p.paymentTime AS LocalDate)")
    List<DailyRevenueDTO> findDailyRevenueBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(p.totalPrice) FROM Payment p " +
            "WHERE p.paymentTime BETWEEN :startDateTime AND :endDateTime")
    BigDecimal calculateTotalRevenueBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
