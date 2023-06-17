package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

    @NotEmpty
    @Email(message = "Email required")
    public String email;
    @NotEmpty
    public String password;
    @NotEmpty
    public String confirmPassword;
}