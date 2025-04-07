package com.parksys.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;
    private String parkingType;
    private LocalDate expiryDate;
    private String memberUnit;
    private boolean isActive = true;
}
