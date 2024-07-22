package com.xb.cinstar.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidRequestException extends RuntimeException{

    private String message;

    public InvalidRequestException(String message) {
        super(message);
    }
}
