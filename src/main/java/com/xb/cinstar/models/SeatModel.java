package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "seat")
public class SeatModel extends  BaseEntity{
    private String name;
    private String rowSeat;
    private  Integer number;
    @Enumerated(EnumType.STRING)
    private EStatusSeat statusSeat;

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY)
    private List<TicketModel> tickets;

    @ManyToMany(mappedBy = "seats",cascade = CascadeType.ALL)
    private List<ScreenModel> screens;
    private boolean status;
    public SeatModel() {

    }
}
