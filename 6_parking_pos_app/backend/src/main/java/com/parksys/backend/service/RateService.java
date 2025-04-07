package com.parksys.backend.service;

import com.parksys.backend.dto.RateRequest;
import com.parksys.backend.dto.RateResponse;
import com.parksys.backend.model.Rate;
import com.parksys.backend.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    public RateResponse create(RateRequest request) {
        Rate rate = Rate.builder()
                .vehicleType(request.getVehicleType())
                .hourlyRate(request.getHourlyRate())
                .maxDailyRate(request.getMaxDailyRate())
                .overnightSurcharge(request.getOvernightSurcharge())
                .build();
        rate = rateRepository.save(rate);
        return toResponse(rate);
    }

    public List<RateResponse> findAll() {
        return rateRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RateResponse findById(Long id) {
        return rateRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    public RateResponse update(Long id, RateRequest request) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found"));
        rate.setVehicleType(request.getVehicleType());
        rate.setHourlyRate(request.getHourlyRate());
        rate.setMaxDailyRate(request.getMaxDailyRate());
        rate.setOvernightSurcharge(request.getOvernightSurcharge());
        rate = rateRepository.save(rate);
        return toResponse(rate);
    }

    public void delete(Long id) {
        rateRepository.deleteById(id);
    }

    private RateResponse toResponse(Rate rate) {
        RateResponse response = new RateResponse();
        response.setId(rate.getId());
        response.setVehicleType(rate.getVehicleType());
        response.setHourlyRate(rate.getHourlyRate());
        response.setMaxDailyRate(rate.getMaxDailyRate());
        response.setOvernightSurcharge(rate.getOvernightSurcharge());
        return response;
    }
}

