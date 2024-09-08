package com.xb.cinstar.dto;

import com.xb.cinstar.models.BaseEntity;
import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.models.TicketOrderModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class FoodRelationDTO extends BaseEntity {


    private Long ticketorderId;
    private  Integer quantity;

    private Long foodId;
}
