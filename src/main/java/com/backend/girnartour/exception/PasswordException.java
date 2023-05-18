package com.backend.girnartour.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordException extends RuntimeException{

    public String password;

    public String confirmPass;

    public PasswordException(String password,String confirmPass){
        super(String.format("Password: %s and confirm-password: %s are not equal",password,confirmPass));
        this.password=password;
        this.confirmPass=confirmPass;
    }
}