package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refreshtoken")
public class RefreshToken extends  BaseEntity{
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserModel userModel;

    private Instant expiryDate;

    @Column(nullable = false, unique = true)
    private String token;



}
