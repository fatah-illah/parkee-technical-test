package com.parksys.backend.service;

import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.repository.ParkingTicketRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ParkingTicketService {

    private final ParkingTicketRepository parkingTicketRepository;

    public ParkingTicketService(ParkingTicketRepository parkingTicketRepository) {
        this.parkingTicketRepository = parkingTicketRepository;
    }

    public ParkingTicket getTicketById(Long id) {
        return parkingTicketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Parking ticket not found with ID: " + id));
    }
}
