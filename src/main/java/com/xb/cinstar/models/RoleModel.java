package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class RoleModel extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
