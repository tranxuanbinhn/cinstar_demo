package com.xb.cinstar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.Date;

@Entity()
@Table(name = "movie")
public class MovieModel extends  BaseEntity{
    private  String name;
    private  Long duration;
    private  String ageLimit;
    private  String subtitle;
    private  String category;
    private  String performers;
    private Date premiere;
    private  String manager;
    private  String movieContent;
    private  String trailer;
    private  String region;
    @Enumerated(EnumType.STRING)
    private Enum<Type> type;

    public MovieModel() {

    }
}
