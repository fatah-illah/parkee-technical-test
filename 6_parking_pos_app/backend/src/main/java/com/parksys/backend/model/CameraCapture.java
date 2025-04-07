package com.parksys.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "camera_captures")
public class CameraCapture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long captureId;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private ParkingTicket ticket;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] entryCameraImg;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] exitCameraImg;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] faceEntryCameraImg;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] faceExitCameraImg;

    private LocalDateTime captureTime;
}
