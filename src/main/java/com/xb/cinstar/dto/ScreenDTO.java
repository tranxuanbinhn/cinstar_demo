package com.xb.cinstar.dto;

import com.xb.cinstar.models.ETypeScreen;
import com.xb.cinstar.models.SeatModel;
import com.xb.cinstar.models.ShowTimeModel;
import com.xb.cinstar.models.TheaterModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
public class ScreenDTO extends AbstractDTO{
    private ETypeScreen type;
    private  String name;
    private Long theaterId;
    private List<Long> seatIds;

    public ScreenDTO() {

    }
}
