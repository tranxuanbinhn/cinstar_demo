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

    private  ETypeMovie typeMovie;

    @ManyToMany(mappedBy = "movies",fetch = FetchType.LAZY)
    private List<TheaterModel> theaters;

    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    private List<ShowTimeModel>  showtimes;

    public MovieModel() {

    }
}
