package com.xb.cinstar.dto;

import lombok.Data;

@Data
public class TheaterDTO extends AbstractDTO{
    private String name;
    private  String address;
    private  String city;
    private  String banner;

    public TheaterDTO() {

    }
}
