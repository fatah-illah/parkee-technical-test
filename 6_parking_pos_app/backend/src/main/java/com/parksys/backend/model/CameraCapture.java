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

    @Column(name = "entry_camera_img", columnDefinition = "bytea")
    private byte[] entryCameraImg;

    @Column(name = "exit_camera_img", columnDefinition = "bytea")
    private byte[] exitCameraImg;

    @Column(name = "face_entry_camera_img", columnDefinition = "bytea")
    private byte[] faceEntryCameraImg;

    @Column(name = "face_exit_camera_img", columnDefinition = "bytea")
    private byte[] faceExitCameraImg;

    private LocalDateTime captureTime;
}
