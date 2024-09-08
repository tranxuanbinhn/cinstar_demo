package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Entity
@Data

@Table(name = "seat")
public class SeatModel extends  BaseEntity{
    private String name;
    private String rowSeat;
    private  Integer number;
    @Enumerated(EnumType.STRING)
    private ETypeSeat typeSeat;
    private boolean status;



    public void setStatus(boolean status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY)
    private List<TicketRelation> tickets;

    @ManyToMany(mappedBy = "seats",cascade = CascadeType.ALL)
    private List<ScreenModel> screens;

    public SeatModel() {

    }
}
