package com.xb.cinstar.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultPOJO implements Serializable {
    private List<MoviePOJO> results;

    public ResultPOJO() {

    }
}
