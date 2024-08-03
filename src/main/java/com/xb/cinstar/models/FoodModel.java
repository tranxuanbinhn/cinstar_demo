package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@Entity
@Data

@Table(name = "food")
public class FoodModel extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private  Integer quantity;

    @ManyToOne()
    @JoinColumn(name = "ticket_order")
    private TicketOrderModel ticketorder;

}
