package com.xb.cinstar.dto;

import com.xb.cinstar.models.BaseEntity;
import com.xb.cinstar.models.EStatusSeat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SeatDTO extends BaseEntity {
    private String name;
    private String rowSeat;
    private  Integer number;
    private EStatusSeat statusSeat;



    public SeatDTO( String rowSeat, Integer number) {
        this.name = rowSeat + number;;
        this.rowSeat = rowSeat;
        this.number = number;
        this.statusSeat = statusSeat;
    }

    public SeatDTO() {

    }
}
