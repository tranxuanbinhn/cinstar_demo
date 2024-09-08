package com.xb.cinstar.dto;
import lombok.Data;

@Data
public class TicketRelationDTO extends  AbstractDTO{

    private Long ticketId;
    private Long showtimeId;
    private Long seatId;
    private Long ticketorderId;
    private  Integer quantity;
}
