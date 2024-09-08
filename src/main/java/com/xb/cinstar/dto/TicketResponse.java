package com.xb.cinstar.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class TicketResponse {
    private LocalDate date;
    private LocalTime time;
    private String nameMovie;
    private String nameTheater;
    private String addressTheater;
    private List<String> numberSeat;
    private List<String> food;
    private BigDecimal total;
    private String orderCode;
    private String img;
    private String screen;

    public TicketResponse(LocalDate date, LocalTime time, String nameMovie, String nameTheater, String addressTheater, List<String> numberSeat, List<String> food, BigDecimal total, String orderCode, String img, String screen) {
        this.date = date;
        this.time = time;
        this.nameMovie = nameMovie;
        this.nameTheater = nameTheater;
        this.addressTheater = addressTheater;
        this.numberSeat = numberSeat;
        this.food = food;
        this.total = total;
        this.orderCode = orderCode;
        this.img = img;
        this.screen = screen;
    }
}
