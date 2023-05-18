package com.backend.girnartour.RequestDTOs;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class PasswordResetRequest {

    @Email(message = "Please Enter a valid Email")
    public String email;
}
