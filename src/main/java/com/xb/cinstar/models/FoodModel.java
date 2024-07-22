package com.xb.cinstar.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "food")
public class FoodModel extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;

    @OneToOne()
    private OrderDetailModel orderdetail;

}
