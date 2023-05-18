package com.backend.girnartour.security;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String userName;
    private String password;
}
