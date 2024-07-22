package com.xb.cinstar.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class MoviePOJO implements Serializable {
    private  Long id;

    private  String title;
    @JsonProperty("release_date")
    private  String releaseDate;
    @JsonProperty("poster_path")
    private String posterPath;
    private  String overview;

    public MoviePOJO() {

    }
}
