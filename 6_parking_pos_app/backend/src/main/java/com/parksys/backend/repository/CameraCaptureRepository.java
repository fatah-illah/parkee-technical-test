package com.parksys.backend.repository;

import com.parksys.backend.model.CameraCapture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CameraCaptureRepository extends JpaRepository<CameraCapture, Long> {
    Optional<CameraCapture> findByTicket_TicketId(Long ticketId);
}
