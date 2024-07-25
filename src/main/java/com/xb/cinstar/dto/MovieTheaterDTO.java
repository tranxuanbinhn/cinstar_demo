package com.xb.cinstar.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieTheaterDTO {
    private List<MovieDTO> movieDTOs;
    private List<TheaterDTO> theaterDTOs;

    public MovieTheaterDTO() {

    }
    public MovieTheaterDTO(List<MovieDTO> movieDTOs, List<TheaterDTO> theaterDTOs) {
        this.movieDTOs = movieDTOs;
        this.theaterDTOs = theaterDTOs;
    }
}
