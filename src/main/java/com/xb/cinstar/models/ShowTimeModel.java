package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Entity

@Table(name = "showtime")
public class ShowTimeModel extends BaseEntity{
    private LocalDateTime date;

    private LocalDateTime endTime;

    @ManyToOne()
    @JoinColumn(name = "movie_id")
    private MovieModel movie;

    @ManyToOne()
    @JoinColumn(name = "screen_id")
    private ScreenModel screen;

    @ManyToOne()
    @JoinColumn(name = "theater_id")
    private TheaterModel theater;

    @OneToMany(mappedBy = "showtime")
    private List<TicketModel> tickets;



    public ShowTimeModel() {

    }
}
