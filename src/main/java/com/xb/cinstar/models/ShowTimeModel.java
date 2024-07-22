package com.xb.cinstar.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "showtime")
public class ShowTimeModel extends BaseEntity{
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "movie_id")
    private MovieModel movie;

    @ManyToOne()
    @JoinColumn(name = "screen_id")
    private ScreenModel screen;

    @ManyToMany(mappedBy = "showtimes")
    private List<TheaterModel> theaters;

    @OneToMany(mappedBy = "showtime")
    private List<TicketModel> tickets;

}
