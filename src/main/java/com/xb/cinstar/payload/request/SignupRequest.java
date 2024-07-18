package com.xb.cinstar.payload.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    private  String userName;

    @NotBlank
    @Size(min = 8)
    private  String password;

    @NotBlank
    @Email
    private  String email;
    @NotBlank
    @Size(min = 10, max = 11)
    private  String phoneNumber;
    @NotBlank
    private  String fullName;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDay;
    @NotBlank

    private  String cic;

    public SignupRequest() {
    }

    public SignupRequest(@NotBlank String userName, @NotBlank @Size(min = 8) String password, @NotBlank @Email String email, @NotBlank @Size(min = 10, max = 11) String phoneNumber, @NotBlank String fullName, @NotBlank Date birthDay, @NotBlank String cic) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.cic = cic;
    }
}
