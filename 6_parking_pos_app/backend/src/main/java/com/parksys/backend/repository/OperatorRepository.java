package com.parksys.backend.repository;

import com.parksys.backend.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
