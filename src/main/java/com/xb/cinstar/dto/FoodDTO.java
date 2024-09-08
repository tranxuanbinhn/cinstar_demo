package com.xb.cinstar.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class FoodDTO extends AbstractDTO{
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String image;

    public FoodDTO() {

    }
}
