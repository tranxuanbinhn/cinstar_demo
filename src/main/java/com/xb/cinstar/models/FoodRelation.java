package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FoodRelation extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_order")
    private TicketOrderModel ticketorder;
    private  Integer quantity;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodModel food;
}
