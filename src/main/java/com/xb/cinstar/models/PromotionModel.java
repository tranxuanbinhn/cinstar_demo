package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "promotion")
public class PromotionModel extends  BaseEntity{
    private String name;
    @Column(name = "condition_promotion")
    private  String condition;
    private  String note;
    private String banner;
    private  String description;
    private Double value;

    @OneToMany(mappedBy = "promotion")
    private List<OrderModel> orders;

    @ManyToMany(mappedBy = "promotions")
    private List<UserModel> users;

    public PromotionModel() {

    }
}
