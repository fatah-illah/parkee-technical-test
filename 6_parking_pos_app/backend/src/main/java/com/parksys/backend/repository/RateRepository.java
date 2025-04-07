package com.parksys.backend.repository;

import com.parksys.backend.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByVehicleTypeIgnoreCase(String vehicleType);
}
