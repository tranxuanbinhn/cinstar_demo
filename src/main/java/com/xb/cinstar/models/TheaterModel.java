package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Entity
@Data

@Table(name = "theater")
public class TheaterModel extends BaseEntity{
    private String name;
    private  String address;
    private  String city;
    private  String banner;

    @ManyToMany(mappedBy = "theaters",fetch = FetchType.LAZY)
    private List<MovieModel> movies;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShowTimeModel> showtimes;

    @OneToMany(mappedBy = "theater")
    private List<ScreenModel> screens;

    public TheaterModel() {

    }
}
