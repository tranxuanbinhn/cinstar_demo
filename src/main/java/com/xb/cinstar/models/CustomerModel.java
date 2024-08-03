package com.xb.cinstar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Data

@Table(name = "customer")
public class CustomerModel extends BaseEntity{
    private String name;
    private String phoneNumber;
    private String email;
    @OneToOne(mappedBy = "customer")
    private OrderModel orderModel;

    public CustomerModel() {

    }
}
