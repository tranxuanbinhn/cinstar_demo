package com.xb.cinstar.dto;

import com.xb.cinstar.models.ETypeTicket;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TicketDTO extends AbstractDTO{
    private ETypeTicket type;

    private BigDecimal price;
    private String qr;
    private Long showtimeId;
    private Long seatId;

    public TicketDTO() {

    }
}
