package com.backend.girnartour.services;

import com.backend.girnartour.constants.UserConstants;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {


//    @Autowired
//    private JavaMailSender mailSender;

//    @Value("${spring.mail.username}")
//    private String sender;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TaskScheduler taskScheduler;


//    public void sendEmail(String email, String forgot_password_link, String resetPasswordLink) {
//
//        Cache cache = cacheManager.getCache("codeCache");
//        assert cache != null;
//        cache.put(UserConstants.CACHE_KEY, generateCode());
//        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
//        passwordResetEmail.setFrom(sender);
//        passwordResetEmail.setTo(email);
//        passwordResetEmail.setSubject("Password Reset Request");
//        passwordResetEmail.setText("To reset your password, Enter the OTP which Expires in 5 Min:\n" + (Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY)).get()));
//        mailSender.send(passwordResetEmail);
//        System.out.println((Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY))).get());
//        try {
//            User user = userDAO.findByEmail(email);
//            if (user != null) {
//                String otp= Objects.requireNonNull(Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY)).get()).toString();
//                System.out.println("OTP to be Saved into DB: " + otp);
//                user.setOtp(otp);
//                userDAO.save(user);
//                System.out.println("Saved into DB: " + Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY)).get());
//            }
//        } catch (Exception ex) {
//            throw new ResourceNotFoundException("User", "Email", email);
//        }
//        scheduleTaskToSetFieldToNull(cache);
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.schedule(() -> cache.evict(email), 5, TimeUnit.MINUTES);
//    }
//
//    private String generateCode() {
//        String random_sequence = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
//        return random_sequence.substring(1, 7);
//        // truncate the code to 6 digits
//    }
//
//
//    public void scheduleTaskToSetFieldToNull(Cache cache) {
//        Runnable task = () -> {
//            User entity = userDAO.findByOtp(Objects.requireNonNull(Objects.requireNonNull(cache.get(UserConstants.CACHE_KEY)).get()).toString());
//            if (entity != null) {
//                entity.setOtp(null);
//                userDAO.save(entity);
//            }
//        };
//        // Build a cron expression for running the task 5 minutes from now
//        String cronExpression = String.format("0 %d %d * * ?",
//                (Calendar.getInstance().get(Calendar.MINUTE) + 5) % 60,
//                Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
//        );
//        taskScheduler.schedule(task, new CronTrigger(cronExpression));
//    }

    public void sendEmail(String email, String forgot_password_link, String resetPasswordLink){
        System.out.println("Email Sent to: "+ email);
    }
}
