package com.xb.cinstar.dto;

import lombok.Data;

import java.util.List;

@Data
public class TheaterDTO extends AbstractDTO{
    private String name;
    private  String address;
    private  String city;
    private  String banner;
    private List<Long> movieIds;

    public TheaterDTO() {

    }
}
