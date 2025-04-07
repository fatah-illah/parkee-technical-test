package com.parksys.backend.controller;

import com.parksys.backend.dto.CheckInRequest;
import com.parksys.backend.dto.CheckOutRequest;
import com.parksys.backend.model.Payment;
import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ParkingTicket> checkIn(@Valid @RequestBody CheckInRequest request) {
        ParkingTicket ticket = parkingService.checkIn(request);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/check-out")
    public ResponseEntity<Payment> checkOut(@Valid @RequestBody CheckOutRequest request) {
        Payment payment = parkingService.checkOut(request);
        return ResponseEntity.ok(payment);
    }
}
