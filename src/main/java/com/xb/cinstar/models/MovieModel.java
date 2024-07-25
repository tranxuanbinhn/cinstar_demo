package com.xb.cinstar.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity()
@Data
@Table(name = "movie")
public class MovieModel extends  BaseEntity{
    private  Long idCode;

    private  String title;
    private  String releaseDate;
    private String posterPath;
    @Column(length = 2000)
    private  String overview;

    private  String runtime;

    private  ETypeMovie typeMovie;

    @ManyToMany(mappedBy = "movies",fetch = FetchType.LAZY)
    @JoinTable(name = "movie_theater", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "theater_id"))
    private List<TheaterModel> theaters;

    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShowTimeModel>  showtimes;

    public MovieModel() {

    }
}
