package com.xb.cinstar.dto;

import com.xb.cinstar.models.BaseEntity;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Data
public class PromotionDTO extends BaseEntity {
    private String name;
    private  String condition;
    private  String note;
    private String banner;
    private  String description;
    private BigDecimal value;
    private DayOfWeek dayOfWeek;


    public PromotionDTO() {

}
}
