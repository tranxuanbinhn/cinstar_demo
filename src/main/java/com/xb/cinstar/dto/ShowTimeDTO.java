package com.xb.cinstar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.ScreenModel;
import com.xb.cinstar.models.TheaterModel;
import com.xb.cinstar.models.TicketModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShowTimeDTO extends AbstractDTO{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Long movieId;
    private Long screenId;
    private Long theaterId;
    private List<Long> ticketIds;
    private LocalDateTime endTime;

}
