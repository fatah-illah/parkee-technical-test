package com.parksys.backend.service;

import com.parksys.backend.model.CameraCapture;
import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.repository.CameraCaptureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CameraCaptureService {

    private final CameraCaptureRepository cameraCaptureRepository;

    public CameraCaptureService(CameraCaptureRepository cameraCaptureRepository) {
        this.cameraCaptureRepository = cameraCaptureRepository;
    }

    public CameraCapture saveEntryCameraCapture(ParkingTicket ticket,
                                                MultipartFile entryCameraImage,
                                                MultipartFile faceEntryCameraImage) throws IOException {
        CameraCapture capture = new CameraCapture();
        capture.setTicket(ticket);
        capture.setEntryCameraImg(entryCameraImage.getBytes());
        capture.setFaceEntryCameraImg(faceEntryCameraImage.getBytes());
        capture.setCaptureTime(LocalDateTime.now());

        return cameraCaptureRepository.save(capture);
    }

    public CameraCapture updateWithExitCameraCapture(CameraCapture capture,
                                                     MultipartFile exitCameraImage,
                                                     MultipartFile faceExitCameraImage) throws IOException {
        capture.setExitCameraImg(exitCameraImage.getBytes());
        capture.setFaceExitCameraImg(faceExitCameraImage.getBytes());

        return cameraCaptureRepository.save(capture);
    }

    public CameraCapture findByTicketId(Long ticketId) {
        return cameraCaptureRepository.findByTicket_TicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("Camera capture not found for ticket ID: " + ticketId));
    }
}
