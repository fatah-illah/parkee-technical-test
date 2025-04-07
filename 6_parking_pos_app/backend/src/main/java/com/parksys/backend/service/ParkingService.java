package com.parksys.backend.service;

import com.parksys.backend.dto.CheckInRequest;
import com.parksys.backend.dto.CheckOutRequest;
import com.parksys.backend.model.*;
import com.parksys.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ParkingService {
    private final VehicleRepository vehicleRepository;

    private final ParkingTicketRepository parkingTicketRepository;

    private final PaymentRepository paymentRepository;

    private final FeeCalculationService feeCalculationService;

    public ParkingService(
            VehicleRepository vehicleRepository,
            ParkingTicketRepository parkingTicketRepository,
            PaymentRepository paymentRepository,
            FeeCalculationService feeCalculationService
    ) {
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.paymentRepository = paymentRepository;
        this.feeCalculationService = feeCalculationService;
    }

    @Transactional
    public ParkingTicket checkIn(CheckInRequest request) {
        // Find or create vehicle
        Vehicle vehicle = vehicleRepository
                .findByPlateNumber(request.getPlateNumber())
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setPlateNumber(request.getPlateNumber());
                    newVehicle.setVehicleType(request.getVehicleType());
                    return vehicleRepository.save(newVehicle);
                });

        // Create new ticket
        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setSlipNumber(request.getSlipNumber());
        ticket.setCheckInTime(LocalDateTime.now());
        ticket.setGateSystem(request.getGateSystem());
        ticket.setStatus("active");

        return parkingTicketRepository.save(ticket);
    }

    @Transactional
    public Payment checkOut(CheckOutRequest request) {
        // Find vehicle
        Vehicle vehicle = vehicleRepository
                .findByPlateNumber(request.getPlateNumber())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Find active ticket
        ParkingTicket ticket = parkingTicketRepository
                .findByVehicleAndStatus(vehicle, "active")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active ticket found for this vehicle"));

        // Update ticket
        LocalDateTime checkOutTime = LocalDateTime.now();
        ticket.setCheckOutTime(checkOutTime);
        ticket.setDuration(Duration.between(ticket.getCheckInTime(), checkOutTime));
        ticket.setStatus("completed");
        parkingTicketRepository.save(ticket);

        // Calculate raw fee
        BigDecimal rawFee = feeCalculationService.calculateFee(ticket);

        // Apply discounts
        BigDecimal totalFee = feeCalculationService.applyDiscounts(
                rawFee,
                request.getMemberType(),
                request.getVoucherCode()
        );

        // Create payment record
        Payment payment = new Payment();
        payment.setTicket(ticket);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setSessionFee(rawFee);
        payment.setVoucherCode(request.getVoucherCode());
        payment.setTotalPrice(totalFee);
        payment.setPaymentTime(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    /**
     * @deprecated Legacy calculation method, kept for reference. Actual fee is handled in FeeCalculationService.
     */
    @Deprecated
    @SuppressWarnings("unused")
    private BigDecimal calculateFee(ParkingTicket ticket) {
        long hours = ticket.getDuration().toHours() + 1;

        if ("MOBIL".equalsIgnoreCase(ticket.getVehicle().getVehicleType())) {
            return new BigDecimal(hours * 5000);
        } else {
            return new BigDecimal(hours * 2000);
        }
    }
}
