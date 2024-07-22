package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "theater")
public class TheaterModel extends BaseEntity{
    private String name;
    private  String address;
    private  String city;
    private  String banner;

    @ManyToMany
    @JoinTable(name = "theater_movie", joinColumns = @JoinColumn(name = "theater_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<MovieModel> movies;

    @ManyToMany
    @JoinTable(name = "theater_showtime", joinColumns = @JoinColumn(name = "theater_id"),
    inverseJoinColumns = @JoinColumn(name = "showtime_id"))
    private List<ShowTimeModel> showtimes;

    @OneToMany(mappedBy = "theater")
    private List<ScreenModel> screens;

    public TheaterModel() {

    }
}
