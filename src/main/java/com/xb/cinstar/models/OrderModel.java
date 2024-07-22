package com.xb.cinstar.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel extends BaseEntity{
    private BigDecimal totalPrice;
    private BigDecimal totalDue;
    private String Ordercode;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    private List<OrderDetailModel> orderdetails;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionModel promotion;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;



}
