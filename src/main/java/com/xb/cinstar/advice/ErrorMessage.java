package com.xb.cinstar.advice;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private  String description;
    private  String message;

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public ErrorMessage() {

    }
}
