package com.xb.cinstar.dto;


import com.xb.cinstar.models.PromotionModel;
import com.xb.cinstar.models.TicketOrderModel;
import com.xb.cinstar.models.UserModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrderDTO {
    private BigDecimal totalPrice;
    private Long id;
    private String Ordercode;
    private Long ticketorderId;
    private Long promationId;
    private Long customerId;
    private Long userId;
    private Long paymentId;
    private Date createDate;


    public OrderDTO() {

    }
}
