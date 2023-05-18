package com.backend.girnartour.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidJwtException extends RuntimeException{

    String msg;

    public InvalidJwtException(String msg){
        super(String.format("This is not a valid Argument %s",msg));
        this.msg=msg;
    }
}
