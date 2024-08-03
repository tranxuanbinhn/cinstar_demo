package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data

@Table(name = "orders")
public class OrderModel extends BaseEntity{
    private BigDecimal totalPrice;

    private String Ordercode;

    @OneToOne(fetch = FetchType.LAZY)
    private TicketOrderModel ticketorder;

    private boolean status;

    @OneToOne
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @OneToOne(mappedBy = "order")
    private PaymentModel payment;
    public OrderModel() {

    }
}
