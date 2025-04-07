package com.parksys.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private ParkingTicket ticket;

    private String paymentMethod;
    private BigDecimal sessionFee;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal promo = BigDecimal.ZERO;
    private BigDecimal totalPrice;
    private String voucherCode;
    private LocalDateTime paymentTime;
}
