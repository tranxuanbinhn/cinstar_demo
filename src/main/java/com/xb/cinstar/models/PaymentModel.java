package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data
public class PaymentModel {
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne()
    private OrderModel order;
}
