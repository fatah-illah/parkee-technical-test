package com.parksys.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operatorId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String role;

    private boolean isActive = true;
}
