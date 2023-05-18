package com.backend.girnartour.security;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String access_token;

    private String refresh_token;

    private String role;

    private String userName;

    private String id;

//    private String id;
}
