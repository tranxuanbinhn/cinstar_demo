package com.xb.cinstar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xb.cinstar.models.ERole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@Data
public class UserDTO extends  AbstractDTO{
    private  String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDay;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String cic;
    @NotBlank
    private  String email;
    private String password;
    @NotBlank
    private  String userName;
    private ERole role;


    public UserDTO() {

    }
}
