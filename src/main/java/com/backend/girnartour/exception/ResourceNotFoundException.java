package com.backend.girnartour.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;



    public ResourceNotFoundException(String resourceName,String fieldName,String fieldValue) {
        super(String.format("%s not found with %s :%s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName,String fieldValue) {
        super(String.format("%s %s",resourceName,fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String fieldValue){
        super(String.format("Jwt token : %s  is expired !",fieldValue));
        this.fieldValue=fieldValue;
    }

}
