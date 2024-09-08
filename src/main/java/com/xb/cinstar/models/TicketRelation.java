package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TicketRelation extends BaseEntity{
    @ManyToOne()
    @JoinColumn(name = "ticket_id")
    private TicketModel ticket;


    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTimeModel showtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private SeatModel seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_order")
    private TicketOrderModel ticketorder;
    private  Integer quantity;
}
