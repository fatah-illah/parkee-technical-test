package com.parksys.backend.controller;

import com.parksys.backend.dto.RateRequest;
import com.parksys.backend.dto.RateResponse;
import com.parksys.backend.service.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping
    public ResponseEntity<RateResponse> create(@Valid @RequestBody RateRequest request) {
        return ResponseEntity.ok(rateService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RateResponse>> getAll() {
        return ResponseEntity.ok(rateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rateService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateResponse> update(@PathVariable Long id, @Valid @RequestBody RateRequest request) {
        return ResponseEntity.ok(rateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
