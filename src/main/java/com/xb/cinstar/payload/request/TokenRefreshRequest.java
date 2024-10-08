package com.xb.cinstar.payload.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public TokenRefreshRequest() {

    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
