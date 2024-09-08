package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data

@Table(name = "food")
public class FoodModel extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String image;

    @OneToMany(mappedBy = "food")
    private List<FoodRelation> ticketorder;

}
