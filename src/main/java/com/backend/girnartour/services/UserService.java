package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.*;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UserUpdateDTO;
import com.backend.girnartour.ResponseDTOs.UserResponseDTO;
import com.backend.girnartour.constants.UserConstants;
import com.backend.girnartour.exception.PasswordException;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.UserDAO;
import com.backend.girnartour.security.JwtAuthResponse;
import com.backend.girnartour.security.JwtTokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;



    public UserResponseDTO saveNewUser(UserRequestDTO userRequestDTO){
        User saveUser=modelMapper.map(userRequestDTO,User.class);
//        String random_sequence= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
        if(userRequestDTO.getRole()==null){
            saveUser.setRole(UserConstants.DEFAULT_ROLE);
        }
        saveUser.setPassword(new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()));
        saveUser.setActive(true);
        saveUser.setUserid(userRequestDTO.getUserName().toUpperCase());
        User savedUser=userDAO.save(saveUser);
        return modelMapper.map(savedUser,UserResponseDTO.class);

    }


//    public ResponseMessage updateResetPasswordToken(PasswordResetRequest passwordResetRequest){
//        String email = passwordResetRequest.getEmail();
//        String token = RandomString.make(30);
//        String path = UserConstants.PATH;
//        String resetPasswordLink = path + "/reset-password?token=" + token;
//        try{
//            emailService.sendEmail(email, "Forgot password link", resetPasswordLink);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        try {
//            User user = userDAO.findByEmail(email);
//            if (user != null) {
//                user.setResetPasswordToken(token);
//                userDAO.save(user);
//            }
//        } catch (Exception ex) {
//           throw new ResourceNotFoundException("User","Email",email);
//        }
//        ResponseMessage responseMessage=new ResponseMessage();
//        responseMessage.setStatus(UserConstants.SUCEESS_STATUS);
//        responseMessage.setText("Please check your email inbox for password reset instructions.");
//        return responseMessage;
//    }

//    Have a method which verifies OTP and then prompt user to enter a new password

//    public ResponseEntity<?> verifyOtp(VerifyOTPRequest otpRequest){
//        Cache cache = cacheManager.getCache("codeCache");
//        String otp= Objects.requireNonNull(Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY)).get()).toString();
//        System.out.println("OTP : "+otp);
//        if(!otpRequest.getOtp().equals(otp)){
//            throw new InvalidOTPException(otp);
//        }
//        User user = userDAO.findByOtp(otp);
//
//        ResponseMessage responseMessage=new ResponseMessage();
//        if (user == null) {
//            responseMessage.setStatus(UserConstants.UNSUCCESS_STATUS);
//            responseMessage.setText("You have entered a wrong OTP or this OTP is already expired !");
//            return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
//        }
//        responseMessage.setStatus(UserConstants.SUCEESS_STATUS);
//        responseMessage.setText("You've successfully Verified your OTP.");
//        return new ResponseEntity<>(modelMapper.map(user,UserResponseDTO.class),HttpStatus.OK);
//    }


//    public ResponseMessage getByResetPasswordToken(ResetPasswordRequest request,String userId) {
//        User user;
//        try{
//            user=userDAO.findById(userId).get();
//        }catch (Exception e){
//            throw new ResourceNotFoundException("User","Id",userId);
//        }
//
//        if(Objects.equals(request.getPassword(), request.getConfirmPassword())){
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String encodedPassword = passwordEncoder.encode(request.getPassword());
//            user.setPassword(encodedPassword);
//            user.setOtp(null);
//            userDAO.save(user);
//        }else{
//            throw new PasswordException(request.getPassword(), request.getConfirmPassword());
//        }
//
//        ResponseMessage responseMessage=new ResponseMessage();
//        responseMessage.setStatus(UserConstants.SUCEESS_STATUS);
//        responseMessage.setText("You've successfully reset your password.");
//        return responseMessage;
//    }

//    public void updatePassword(User user, String newPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(encodedPassword);
//        user.setResetPasswordToken(null);
//        userDAO.save(user);
//    }

    public User getUserByEmail(String username) {
        User user;
        try{
            user=userDAO.findByEmail(username);
        }catch (Exception e){
            throw new ResourceNotFoundException("User","Email",username);
        }
        return user;
    }

    public ResponseEntity<?> getAllUsers(){
        List<User> users=userDAO.findAll();
        List<UserResponseDTO> responseDTOS=users.stream()
                .map(user -> modelMapper.map(user,UserResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<?> getUserById(String id){
        User user;
        try{
            user=userDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("User","ID",id);
        }
        UserResponseDTO responseDTO=modelMapper.map(user,UserResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> updateUser(String id, UserUpdateDTO userUpdateDTO){

        if(userUpdateDTO.getPassword()!=null && userUpdateDTO.getConfirmPass()!=null){
            if(!(userUpdateDTO.getPassword().equals(userUpdateDTO.getConfirmPass()))){
                throw new PasswordException(userUpdateDTO.getPassword(), userUpdateDTO.getConfirmPass());
            }
        }
        User existingUser = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","ID",id));
        BeanUtils.copyProperties(userUpdateDTO, existingUser, getNullPropertyNames(userUpdateDTO));
        User updatedUser = userDAO.save(existingUser);
        UserResponseDTO userResponseDTO=modelMapper.map(updatedUser, UserResponseDTO.class);
        return ResponseEntity.ok(userResponseDTO);
    }

    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public ResponseEntity<?> deleteUserById(String id){
        User user;
        try{
            user=userDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("User","ID",id);
        }
        userDAO.delete(user);
        return new ResponseEntity<>("User deleted Successfully with ID: "+id,HttpStatus.OK);
    }


    public JwtAuthResponse refreshToken(String refreshToken, HttpServletRequest request){
        JwtAuthResponse authResponse=new JwtAuthResponse();

        System.out.println(refreshToken);
        String userName=null;
        String token=null;

        if(refreshToken!=null && refreshToken.startsWith("Bearer ")) {
            token = refreshToken.substring(7);
            userName = jwtTokenHelper.getUsernameFromToken(token);

        }
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(userName);

            if(jwtTokenHelper.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        UserDetails newUserDetails=userDetailsService.loadUserByUsername(userName);
        authResponse.setRefresh_token(token);
        authResponse.setAccess_token(jwtTokenHelper.generateToken(newUserDetails));
        User user=getUserByEmail(newUserDetails.getUsername());
        authResponse.setRole(user.getRole());
        authResponse.setUserName(user.getUserName());
        authResponse.setId(user.getUserName());
        return authResponse;
    }

    public ResponseEntity<?> changeUserPass(ChangePassword changePassword) {
       User user;
        try{
            user=userDAO.findByEmail(changePassword.getEmail());
        }catch (Exception e){
            throw new ResourceNotFoundException("User","Email",changePassword.getEmail());
        }
        if (changePassword.getPassword().equals(changePassword.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(changePassword.getPassword()));
            userDAO.save(user);
        }else {
            throw new PasswordException("Password","Confirm-Password");
        }
        UserResponseDTO responseDTO=modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}