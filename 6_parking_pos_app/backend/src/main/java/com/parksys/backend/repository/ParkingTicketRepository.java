package com.parksys.backend.repository;

import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
    List<ParkingTicket> findByVehicleAndStatus(Vehicle vehicle, String status);
    Optional<ParkingTicket> findBySlipNumber(String slipNumber);
}
