package com.xb.cinstar.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket")
public class TicketModel extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ETypeTicket type;

    private BigDecimal price;
    private String qr;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowTimeModel showtime;

    @OneToOne(mappedBy = "ticket",fetch = FetchType.LAZY)
    private SeatModel seat;

    @OneToOne()
    private OrderDetailModel orderdetail;

}
