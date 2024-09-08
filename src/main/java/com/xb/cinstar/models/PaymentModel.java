package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data
public class PaymentModel extends BaseEntity{
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;


    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;


    @OneToOne()
    private OrderModel order;
}
