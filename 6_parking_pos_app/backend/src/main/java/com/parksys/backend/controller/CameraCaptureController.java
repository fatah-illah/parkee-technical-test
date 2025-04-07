package com.parksys.backend.controller;

import com.parksys.backend.model.CameraCapture;
import com.parksys.backend.model.ParkingTicket;
import com.parksys.backend.service.CameraCaptureService;
import com.parksys.backend.service.ParkingTicketService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/camera")
public class CameraCaptureController {

    private final CameraCaptureService cameraCaptureService;

    private final ParkingTicketService parkingTicketService;

    public CameraCaptureController(CameraCaptureService cameraCaptureService, ParkingTicketService parkingTicketService) {
        this.cameraCaptureService = cameraCaptureService;
        this.parkingTicketService = parkingTicketService;
    }

    @PostMapping("/entry/{ticketId}")
    public ResponseEntity<CameraCapture> uploadEntryImages(
            @PathVariable Long ticketId,
            @RequestParam("entryCameraImage") MultipartFile entryCameraImage,
            @RequestParam("faceEntryCameraImage") MultipartFile faceEntryCameraImage) throws IOException {

        ParkingTicket ticket = parkingTicketService.getTicketById(ticketId);
        CameraCapture capture = cameraCaptureService.saveEntryCameraCapture(
                ticket, entryCameraImage, faceEntryCameraImage);

        return ResponseEntity.ok(capture);
    }

    @PostMapping("/exit/{ticketId}")
    public ResponseEntity<CameraCapture> uploadExitImages(
            @PathVariable Long ticketId,
            @RequestParam("exitCameraImage") MultipartFile exitCameraImage,
            @RequestParam("faceExitCameraImage") MultipartFile faceExitCameraImage) throws IOException {

        // ParkingTicket ticket = parkingTicketService.getTicketById(ticketId);
        CameraCapture capture = cameraCaptureService.findByTicketId(ticketId);

        CameraCapture updatedCapture = cameraCaptureService.updateWithExitCameraCapture(
                capture, exitCameraImage, faceExitCameraImage);

        return ResponseEntity.ok(updatedCapture);
    }

    @GetMapping(value = "/entry-image/{ticketId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getEntryImage(@PathVariable Long ticketId) {
        CameraCapture capture = cameraCaptureService.findByTicketId(ticketId);
        return ResponseEntity.ok(capture.getEntryCameraImg());
    }

    @GetMapping(value = "/exit-image/{ticketId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getExitImage(@PathVariable Long ticketId) {
        CameraCapture capture = cameraCaptureService.findByTicketId(ticketId);
        return ResponseEntity.ok(capture.getExitCameraImg());
    }
}
