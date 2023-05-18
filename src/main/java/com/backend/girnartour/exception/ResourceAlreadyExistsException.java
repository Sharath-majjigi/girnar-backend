package com.backend.girnartour.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException{

    public String value;

    public String resource;

    public  ResourceAlreadyExistsException(String value,String resource){
        super(String.format("%s  already exists with  %s ",value,resource));
        this.value=value;
        this.resource=resource;
    }
}
