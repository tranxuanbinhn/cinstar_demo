package com.xb.cinstar.dto;

import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.models.OrderModel;
import com.xb.cinstar.models.TicketModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Data
public class TicketOrderDTO {

     private List<Long> ticketIds;
    private List<Long> foodIds;


    public TicketOrderDTO() {

    }
}
