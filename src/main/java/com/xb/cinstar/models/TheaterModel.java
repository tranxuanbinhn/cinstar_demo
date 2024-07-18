package com.xb.cinstar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "theater")
public class TheaterModel extends BaseEntity{
    private String name;
    private  String address;
    private  String city;
    private  String banner;

}
