package com.xb.cinstar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xb.cinstar.models.ETypeMovie;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TheaterModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieDTO extends AbstractDTO{
    private  String title;

    private  String releaseDate;
    private Long idCode;
    private String posterPath;
    private  String overview;
    private  String runtime;

    private ETypeMovie typeMovie;

    private List<Long> theaterIds;

    private List<Long>  showtimeIds;

    public MovieDTO() {
 }
}
