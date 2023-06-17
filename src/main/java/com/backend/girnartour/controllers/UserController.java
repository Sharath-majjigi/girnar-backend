package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.*;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UserUpdateDTO;
import com.backend.girnartour.ResponseDTOs.UserResponseDTO;
import com.backend.girnartour.constants.UserConstants;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.UserDAO;
import com.backend.girnartour.security.JwtAuthResponse;
import com.backend.girnartour.security.JwtTokenHelper;
import com.backend.girnartour.services.CustomUserDetailService;
import com.backend.girnartour.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    private String secretKey= UserConstants.JWT_SECRET;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveNewUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO responseDTO=userService.saveNewUser(userRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO updateDTO, @PathVariable String id){
        return userService.updateUser(id,updateDTO);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword){
        return userService.changeUserPass(changePassword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        return userService.deleteUserById(id);
    }

//    @PostMapping("/forgot_password")
//    public ResponseMessage processForgotPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
//        return userService.updateResetPasswordToken(passwordResetRequest);
//    }
//
//    @PostMapping("/reset_password/{userId}")
//    public ResponseMessage processResetPassword(@RequestBody ResetPasswordRequest request,@PathVariable String userId) {
//        return userService.getByResetPasswordToken( request ,userId);
//    }

//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOTP(@Valid @RequestBody VerifyOTPRequest request){
//        return userService.verifyOtp(request);
//    }


    @PostMapping("/authenticate")
    public JwtAuthResponse generateToken(@RequestBody UserRequestDTO userDTO) {
        User user;
        try {
             user=userDAO.findByEmail(userDTO.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with ", "email:" + userDTO.getEmail() + " and password:" + userDTO.getPassword());
        }
            user.setLastLogin(Timestamp.from(Instant.now()));
            userDAO.save(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());
            JwtAuthResponse response = new JwtAuthResponse();
            response.setRole(user.getRole());
            response.setId(user.getUserid());
            response.setUserName(user.getUserName());
            response.setAccess_token(jwtTokenHelper.generateToken(userDetails));
            response.setRefresh_token(generateRefreshToken(userDetails));
            return response;
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10045 * 60 *1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }


    @GetMapping("/refresh-token")
    public JwtAuthResponse refreshToken(HttpServletRequest request){
        String refreshToken=request.getHeader("Authorization");
        return userService.refreshToken(refreshToken,request);
    }

}
