package com.xb.cinstar.payload.request;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private  String userName;

    @NotBlank
    private  String password;

    public LoginRequest() {

    }
}
