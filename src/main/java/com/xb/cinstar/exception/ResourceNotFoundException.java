package com.xb.cinstar.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
