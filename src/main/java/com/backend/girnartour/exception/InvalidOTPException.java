package com.backend.girnartour.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidOTPException extends RuntimeException{

    public String otp;

    public InvalidOTPException(String otp){
        super(String.format("OTP %s is invalid or expired try again !",otp));
        this.otp=otp;
    }
}
