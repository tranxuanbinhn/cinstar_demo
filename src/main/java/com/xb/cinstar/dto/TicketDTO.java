package com.xb.cinstar.dto;

import com.xb.cinstar.models.ETypeTicket;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TicketDTO extends AbstractDTO{
    private ETypeTicket type;

    private BigDecimal price;
    private String name;




    public TicketDTO() {

    }
}
