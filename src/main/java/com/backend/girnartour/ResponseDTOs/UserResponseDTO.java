package com.backend.girnartour.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String userid;

    private String userName;

    private String email;

    private String password;

    private String role;

    private boolean active;

    private Timestamp lastLogin;

}
