package com.xb.cinstar.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "screen")
@Data
public class ScreenModel extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ETypeScreen type;
    private  String name;

    @OneToMany(mappedBy = "screen")
    private List<ShowTimeModel> showtimes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theater_id")
    private TheaterModel theater;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "screen_seat", joinColumns = @JoinColumn(name = "screen_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private List<SeatModel> seats;

    public ScreenModel() {
    }
}
