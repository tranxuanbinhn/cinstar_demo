package com.xb.cinstar.dto;

import com.xb.cinstar.models.BaseEntity;

import com.xb.cinstar.models.ETypeSeat;

import lombok.Data;

@Data
public class SeatDTO extends BaseEntity {
    private String name;
    private String rowSeat;
    private  Integer number;
    private  boolean status;
    private ETypeSeat typeSeat;



    public SeatDTO() {

    }
}
