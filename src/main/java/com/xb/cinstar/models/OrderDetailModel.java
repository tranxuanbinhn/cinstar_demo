package com.xb.cinstar.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetailModel extends BaseEntity{
    private  Integer quantity;

    @OneToOne(mappedBy = "orderdetail",fetch = FetchType.LAZY)
    private TicketModel ticket;

    @OneToOne(mappedBy = "orderdetail",fetch = FetchType.LAZY)
    private FoodModel food;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

}
