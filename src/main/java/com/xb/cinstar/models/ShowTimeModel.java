package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@Data
@Entity

@Table(name = "showtime")
public class ShowTimeModel extends BaseEntity{
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime dateTime;
    private LocalDateTime endTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime() {
        this.dateTime = date.atTime(time);
    }

    @ManyToOne()
    @JoinColumn(name = "movie_id")
    private MovieModel movie;

    @ManyToOne()
    @JoinColumn(name = "screen_id")
    private ScreenModel screen;

    @ManyToOne()
    @JoinColumn(name = "theater_id")
    private TheaterModel theater;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<TicketRelation> tickets;




    public ShowTimeModel() {

    }
}
