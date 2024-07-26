package com.xb.cinstar.dto;

import lombok.Data;

@Data
public class CustomerDTO extends AbstractDTO{

    private String name;
    private String phoneNumber;
    private String email;

    public CustomerDTO() {

    }
}
