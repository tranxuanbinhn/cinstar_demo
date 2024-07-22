package com.xb.cinstar.dto;

import com.xb.cinstar.models.BaseEntity;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PromotionDTO extends BaseEntity {
    private String name;
    private  String condition;
    private  String note;
    private String banner;
    private  String description;
    private Double value;

    public PromotionDTO() {
}
}
