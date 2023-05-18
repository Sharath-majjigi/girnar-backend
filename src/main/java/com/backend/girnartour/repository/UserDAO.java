package com.backend.girnartour.repository;

import com.backend.girnartour.ResponseDTOs.UserResponseDTO;
import com.backend.girnartour.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,String> {

    User findByEmail(String email);

//    User findByResetPasswordToken(String token);
//
//    User findByOtp(String otp);
}
