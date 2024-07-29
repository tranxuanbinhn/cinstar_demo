package com.xb.cinstar.dto;

import com.xb.cinstar.models.OrderModel;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class PaymentDTO extends AbstractDTO{

    private BigDecimal amount;


    private LocalDateTime paymentDate;


    private String paymentMethod;

    private Long orderId;
}
