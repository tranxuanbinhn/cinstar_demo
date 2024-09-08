package com.xb.cinstar.payload.response;

import lombok.Data;

import java.util.List;
@Data
public class PageResponse<T> {
    private int page;
    private int limit;
    private double counts;
    private List<T> results;

    public PageResponse() {

    }
}
