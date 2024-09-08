package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data

@Table(name = "ticket")
public class TicketModel extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ETypeTicket type;


    private BigDecimal price;

    private String name;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<TicketRelation> ticketRelation;
    public TicketModel() {

    }
}
