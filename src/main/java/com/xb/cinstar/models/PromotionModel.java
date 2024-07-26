package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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
    private BigDecimal value;
    private DayOfWeek dayOfWeek;



    @ManyToMany(mappedBy = "promotions")
    private List<UserModel> users;

    public PromotionModel() {

    }
}
