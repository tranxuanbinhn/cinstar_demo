package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ticket_order")
public class TicketOrderModel extends BaseEntity{


    @OneToMany(mappedBy = "ticketorder")
    private List<TicketModel> tickets;

    @OneToMany(mappedBy = "ticketorder")
    private List<FoodModel> foods;

    @OneToOne(mappedBy = "ticketorder")
    private OrderModel order;

    public TicketOrderModel() {

    }
}
