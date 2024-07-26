package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "ticket")
public class TicketModel extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ETypeTicket type;
    private  Integer quantity;

    private BigDecimal price;
    private String qr;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTimeModel showtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private SeatModel seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_order")
    private TicketOrderModel ticketorder;

    public TicketModel() {

    }
}
