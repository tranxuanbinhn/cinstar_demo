package com.xb.cinstar.pojo;

import com.xb.cinstar.models.ETypeMovie;
import lombok.Data;

import java.util.List;

@Data
public class RequestPOJO {
    private List<Long> theaterIds;
    ETypeMovie typeMovie;
    MoviePOJO moviePOJO;

    public RequestPOJO() {

    }
}
